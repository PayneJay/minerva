package com.minerva.business.article.list;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.minerva.BR;
import com.minerva.business.mine.login.LoginActivity;
import com.minerva.common.Constants;
import com.minerva.R;
import com.minerva.business.article.list.model.ArticleBean;
import com.minerva.business.article.list.model.ArticleModel;
import com.minerva.base.BaseViewModel;
import com.minerva.network.NetworkObserver;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class ArticleListViewModel extends BaseViewModel {
    public ObservableBoolean refreshing = new ObservableBoolean();
    public ObservableBoolean isRecommendGone = new ObservableBoolean();
    public ObservableList<ArticleItemViewModel> items = new ObservableArrayList<>();
    public OnItemBind<ArticleItemViewModel> articleItemBind = new OnItemBind<ArticleItemViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, ArticleItemViewModel item) {
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
    private int mIndex;

    ArticleListViewModel(Context context, int index) {
        super(context);
        this.mIndex = index;
        requestServer();
    }

    public int[] getColors() {
        return new int[]{R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark};
    }

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            requestServer();
        }
    };

    public void loadMore() {
        Log.e(Constants.TAG, "loadMore...");
        for (int i = 0; i < 15; i++) {
            items.add(new ArticleItemViewModel(context));
        }
    }

    public void goLogin() {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    private void createViewModel() {
        items.clear();
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

    private void requestServer() {
        refreshing.set(true);
        ArticleModel.getInstance().getArticleList(mIndex, 1, new NetworkObserver<ArticleBean>() {
            @Override
            public void onSuccess(ArticleBean articleBean) {
                refreshing.set(false);
                Log.i(Constants.TAG, "getArticleList===success " + articleBean.isSuccess());
                mData.clear();
                mData.addAll(articleBean.getArticles());
                createViewModel();
            }

            @Override
            public void onFailure() {
                refreshing.set(false);
                Log.i(Constants.TAG, "getArticleList===failure");
                mData.clear();
                mData.addAll(ArticleModel.getInstance().generateArticlesData());
                createViewModel();
            }
        });
    }
}
