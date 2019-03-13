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
                    itemBinding.set(BR.periodicalTitleVM, R.layout.item_periodical_layout);
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
            getPeriodicalDetail();
        }
    };

    public void loadMore() {
        Log.e(Constants.TAG, "loadMore...");
        for (int i = 0; i < 15; i++) {
            items.add(new ArticleItemViewModel(context));
        }
    }

    private void getPeriodicalDetail() {
        refreshing.set(true);
        PeriodicalModel.getInstance().getPeriodicalDetail(periodicalID, new NetworkObserver<ArticleBean>() {
            @Override
            public void onSuccess(ArticleBean articleBean) {
                refreshing.set(false);
                PeriodicalTitleViewModel viewModel = new PeriodicalTitleViewModel(context);
                viewModel.imgUrl.set(image);
                viewModel.name.set(name);
                items.add(viewModel);
                mData.clear();
                mData.addAll(articleBean.getArticles());
                createViewModel();
            }

            @Override
            public void onFailure() {
                refreshing.set(false);
            }
        });
    }

    private void createViewModel() {
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
