package com.minerva.business.site.detail;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.article.list.ArticleItemViewModel;
import com.minerva.business.article.list.model.ArticleBean;
import com.minerva.common.Constants;
import com.minerva.network.NetworkObserver;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class PeriodicalDetailViewModel extends BaseViewModel {
    public ObservableField<String> title = new ObservableField<>();
    public ObservableBoolean refreshing = new ObservableBoolean();
    public ObservableList<BaseViewModel> items = new ObservableArrayList<>();
    public OnItemBind<BaseViewModel> periodicalItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            switch (item.getViewType()) {
                case Constants.RecyclerItemType.ARTICLE_COMMON_TYPE:
                    itemBinding.set(BR.articleItemVM, R.layout.item_article_common_layout);
                    break;
                case Constants.RecyclerItemType.PERIODICAL_TYPE:
                    itemBinding.set(BR.periodicalTitleVM, R.layout.item_periodical_title_layout);
                    break;
                case Constants.RecyclerItemType.BLANK_TYPE:
                    itemBinding.set(BR.vmBlank, R.layout.item_blank_layout);
                    break;
            }
        }
    };
    public View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };
    public RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            if (position > 0) {
                title.set(name);
            } else {
                title.set("");
            }
        }
    };
    private String periodicalID, image, name;
    private int mCurrentPage; //当前页数
    private String mLastID; //最后一条id
    private List<ArticleBean.ArticlesBean> mData = new ArrayList<>();

    PeriodicalDetailViewModel(Context context) {
        super(context);

        periodicalID = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.PERIODICAL_ID);
        image = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.PERIODICAL_IMAGE);
        name = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.PERIODICAL_NAME);
        getPeriodicalDetail();
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
            getPeriodicalDetail();
        }
    };

    public void loadMore() {
        mCurrentPage++;
        getPeriodicalDetail();
    }

    private void getPeriodicalDetail() {
        PeriodicalModel.getInstance().getPeriodicalDetail(periodicalID, mCurrentPage, mLastID, new NetworkObserver<ArticleBean>() {
            @Override
            public void onSuccess(ArticleBean articleBean) {
                refreshing.set(false);
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
     * @param articleBean
     */
    private void handleData(ArticleBean articleBean) {
        if (articleBean == null) {
            return;
        }

        List<ArticleBean.ArticlesBean> articles = articleBean.getArticles();
        mLastID = articles.get(articles.size() - 1).getId();
        mData.clear();
        mData.addAll(articles);
    }

    private void createViewModel() {
        if (mCurrentPage == 0) {
            items.clear();

            PeriodicalTitleViewModel titleViewModel = new PeriodicalTitleViewModel(context);
            titleViewModel.imgUrl.set(image);
            titleViewModel.name.set(name);
            items.add(titleViewModel);
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
}
