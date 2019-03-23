package com.minerva.business.article.list;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;

import com.minerva.BR;
import com.minerva.business.mine.login.LoginActivity;
import com.minerva.common.BlankViewModel;
import com.minerva.common.Constants;
import com.minerva.R;
import com.minerva.business.article.list.model.ArticleBean;
import com.minerva.business.article.list.model.ArticleModel;
import com.minerva.base.BaseViewModel;
import com.minerva.common.EventMsg;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class ArticleListViewModel extends BaseViewModel {
    public ObservableBoolean refreshing = new ObservableBoolean();
    public ObservableBoolean isRecommendGone = new ObservableBoolean();
    public ObservableList<BaseViewModel> items = new ObservableArrayList<>();
    public OnItemBind<BaseViewModel> articleItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            switch (item.getViewType()) {
                case Constants.RecyclerItemType.ARTICLE_COMMON_TYPE:
                    itemBinding.set(BR.articleItemVM, R.layout.item_article_common_layout);
                    break;
                case Constants.RecyclerItemType.BLANK_TYPE:
                    itemBinding.set(BR.vmBlank, R.layout.item_blank_layout);
                    break;
            }
        }
    };
    private List<ArticleBean.ArticlesBean> mData = new ArrayList<>();
    private BlankViewModel mBlankVM;
    private String mLastID; //最后一条id
    private int mCurrentTab; //当前Tab
    private int mCurrentPage; //当前页数
    private boolean hasNext;

    ArticleListViewModel(Context context, int tab) {
        super(context);
        this.mCurrentTab = tab;
        EventBus.getDefault().register(this);
        requestServer();
    }

    public ArticleListViewModel(Context context, String className) {
        super(context);
    }

    public int[] getColors() {
        return new int[]{R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark};
    }

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mCurrentPage = 0;
            mLastID = "";
            refreshing.set(true);
            requestServer();
        }
    };

    public void loadMore() {
        if (!hasNext) {
            return;
        }

        mCurrentPage++;
        requestServer();
    }

    public void goLogin() {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    public void onEvent(EventMsg eventMsg) {
        super.onEvent(eventMsg);
        if (TextUtils.equals(eventMsg.getMsg(), Constants.EventMsgKey.LOGIN_SUCCESS)) {
            requestServer();
        }
    }

    protected void createViewModel() {
        if (mBlankVM != null && mCurrentPage == 0) {
            mBlankVM.setStatus(Constants.PageStatus.NO_DATA);
        }
        if (mCurrentPage == 0) {
            items.clear();
        }

        for (int i = 0; i < mData.size(); i++) {
            ArticleItemViewModel viewModel = new ArticleItemViewModel(context);
            ArticleBean.ArticlesBean articlesBean = mData.get(i);
            viewModel.content.set(articlesBean.getTitle());
            viewModel.date.set(articlesBean.getRectime());
            viewModel.imgUrl.set(articlesBean.getImg());
            viewModel.articleID = articlesBean.getId();
            items.add(viewModel);
        }
    }

    protected void requestServer() {
        if (!CommonUtils.isNetworkAvailable(context)) {
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

        ArticleModel.getInstance().getArticleList(mCurrentTab, 1, mLastID, mCurrentPage, new NetworkObserver<ArticleBean>() {
            @Override
            public void onSuccess(ArticleBean articleBean) {
                refreshing.set(false);
                isRecommendGone.set(true);
                handleData(articleBean);
                createViewModel();
            }

            @Override
            public void onFailure() {
                refreshing.set(false);
            }
        });
    }

    /**
     * 处理返回数据
     *
     * @param articleBean 返回数据
     */
    private void handleData(ArticleBean articleBean) {
        if (articleBean == null) {
            return;
        }

        List<ArticleBean.ArticlesBean> articles = articleBean.getArticles();
        mCurrentPage = articleBean.getPn();
        hasNext = articleBean.isHas_next();
        mLastID = articles.get(articles.size() - 1).getId();
        mData.clear();
        mData.addAll(articles);
    }
}
