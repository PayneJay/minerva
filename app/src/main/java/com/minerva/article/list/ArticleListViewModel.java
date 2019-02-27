package com.minerva.article.list;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import com.minerva.BR;
import com.minerva.common.Constants;
import com.minerva.R;
import com.minerva.article.list.model.ArticleBean;
import com.minerva.article.list.model.ArticleModel;
import com.minerva.base.BaseViewModel;
import com.minerva.network.NetworkObserver;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class ArticleListViewModel extends BaseViewModel {
    public ObservableBoolean refreshing = new ObservableBoolean();
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

    ArticleListViewModel(Context context) {
        super(context);
//        requestServer();
        createViewModel();
    }

    public int[] getColors() {
        return new int[]{R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark};
    }

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshing.set(true);
            requestServer();
        }
    };

    public void loadMore() {
        Log.e(Constants.TAG, "loadMore...");
        for (int i = 0; i < 15; i++) {
            items.add(new ArticleItemViewModel(context));
        }
    }

    private void createViewModel() {
        items.clear();
        for (int i = 0; i < 15; i++) {
            items.add(new ArticleItemViewModel(context));
        }
    }

    private void requestServer() {
        ArticleModel.getInstance().getArticleList("101000000", 1, new NetworkObserver<ArticleBean>() {
            @Override
            public void onSuccess(ArticleBean articleBean) {
                refreshing.set(false);
                Log.i(Constants.TAG, "getArticleList===success " + articleBean.isSuccess());
            }

            @Override
            public void onFailure() {
                refreshing.set(false);
                Log.i(Constants.TAG, "getArticleList===failure");
            }
        });
    }
}
