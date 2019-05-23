package com.minerva.business.mine.read;

import android.content.Context;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.view.View;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.business.article.detail.model.ArticleDetailBean;
import com.minerva.business.article.detail.model.ArticleDetailModel;
import com.minerva.business.article.list.ArticleItemViewModel;
import com.minerva.business.article.list.ArticleListViewModel;
import com.minerva.business.article.list.model.ArticleListBean;
import com.minerva.business.mine.read.model.ReadModel;
import com.minerva.common.BlankViewModel;
import com.minerva.common.Constants;
import com.minerva.common.GlobalData;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtil;
import com.minerva.utils.ResourceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class ReadLaterViewModel extends ArticleListViewModel {
    public ObservableField<String> mTitle = new ObservableField<>(ResourceUtil.getString(R.string.mine_to_be_read));
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };
    private List<ArticleDetailBean.ArticleBean> mLocalData = new ArrayList<>();
    private BlankViewModel mBlankVM;
    private String mKey;

    ReadLaterViewModel(Context context) {
        super(context, context.getClass().getSimpleName());
        mKey = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.COME_FROM_MINE);
        setContentByMode();
    }

    @Override
    protected void requestServer() {
        if (TextUtils.equals(mKey, Constants.KeyExtra.READ_HISTORY_MAP)) {
            refreshing.set(false);
            return;
        }

        if (!CommonUtil.isNetworkAvailable(context)) {
            refreshing.set(false);
            if (mBlankVM == null) {
                mBlankVM = new BlankViewModel(context);
            }
            if (mCurrentPage == 0) {
                items.clear();
                mBlankVM.setStatus(Constants.PageStatus.NETWORK_EXCEPTION);
                items.add(mBlankVM);
            }
            return;
        }

        refreshing.set(true);
        ReadModel.getInstance().getLateList(new NetworkObserver<ArticleListBean>() {
            @Override
            public void onSuccess(ArticleListBean articleListBean) {
                refreshing.set(false);
                mData.clear();
                mData.addAll(articleListBean.getArticles());
                createViewModel();
                addToReadLater(articleListBean.getArticles());
            }

            @Override
            public void onFailure(String msg) {
                refreshing.set(false);
            }
        });
    }

    /**
     * 添加到待读列表
     *
     * @param articles 待读文章列表
     */
    private void addToReadLater(List<ArticleDetailBean.ArticleBean> articles) {
        for (ArticleDetailBean.ArticleBean article : articles) {
            if (null == article) {
                continue;
            }
            ArticleDetailModel.getInstance().addArticleWithKey(context, article, Constants.KeyExtra.READ_LATER_MAP);
        }
    }

    private void createViewModelByLocal() {
        items.clear();

        for (int i = 0; i < mLocalData.size(); i++) {
            ArticleItemViewModel viewModel = new ArticleItemViewModel(context);
            ArticleDetailBean.ArticleBean articlesBean = mLocalData.get(i);
            viewModel.content.set(articlesBean.getTitle());
            viewModel.date.set(articlesBean.getTime());
            viewModel.imgUrl.set(articlesBean.getImg());
            viewModel.articleID = articlesBean.getId();
            items.add(viewModel);
        }
    }

    /**
     * 设置本地缓存的待读数据
     *
     * @param context context
     */
    private void setReadLaterData(Context context) {
        Map<String, Object> readLater = ArticleDetailModel.getInstance().getArticlesByKey(context, mKey);
        if (readLater.size() <= 0) {
            if (mBlankVM == null) {
                mBlankVM = new BlankViewModel(context);
            }
            mBlankVM.setStatus(Constants.PageStatus.NO_DATA);
            items.add(mBlankVM);
            return;
        }

        //反向遍历map
        ListIterator<Map.Entry<String, Object>> iterator = new ArrayList<>(readLater.entrySet()).listIterator(readLater.size());
        while (iterator.hasPrevious()) {
            Map.Entry<String, Object> previous = iterator.previous();
            if (previous.getValue() instanceof ArticleDetailBean.ArticleBean) {
                mLocalData.add((ArticleDetailBean.ArticleBean) previous.getValue());
            }
        }

        createViewModelByLocal();
    }

    /**
     * 根据页面类型设置加载内容
     */
    private void setContentByMode() {
        switch (mKey) {
            case Constants.KeyExtra.READ_LATER_MAP:
                mTitle.set(ResourceUtil.getString(R.string.mine_to_be_read));
                if (GlobalData.getInstance().isLogin()) {
                    requestServer();
                } else {
                    setReadLaterData(context);
                }
                break;
            case Constants.KeyExtra.READ_HISTORY_MAP:
                mTitle.set(ResourceUtil.getString(R.string.mine_read_history));
                setReadLaterData(context);
                break;
        }
    }
}
