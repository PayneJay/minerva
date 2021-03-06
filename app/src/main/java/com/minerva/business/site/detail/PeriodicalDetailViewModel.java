package com.minerva.business.site.detail;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.article.detail.model.ArticleDetailBean;
import com.minerva.business.article.list.ArticleItemViewModel;
import com.minerva.common.BlankViewModel;
import com.minerva.common.Constants;
import com.minerva.common.viewmodel.RefreshListViewModel;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtil;
import com.minerva.utils.ResourceUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class PeriodicalDetailViewModel extends RefreshListViewModel {
    public ObservableField<String> title = new ObservableField<>();
    public ObservableList<BaseViewModel> items = new ObservableArrayList<>();
    public OnItemBind<BaseViewModel> periodicalItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            switch (item.getViewType()) {
                case Constants.RecyclerItemType.ARTICLE_COMMON_TYPE:
                    itemBinding.set(BR.articleItemVM, R.layout.item_article_common_layout);
                    break;
                case Constants.RecyclerItemType.PERIODICAL_TITLE_TYPE:
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
    protected List<ArticleDetailBean.ArticleBean> mData = new ArrayList<>();
    protected BlankViewModel mBlankVM;
    protected String name;
    protected boolean hasNext;
    protected int mCurrentPage; //当前页数
    private PeriodicalTitleViewModel titleViewModel;
    private String periodicalID;
    private String mLastID; //最后一条id

    PeriodicalDetailViewModel(Context context) {
        super(context);

        String image = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.PERIODICAL_IMAGE);
        periodicalID = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.PERIODICAL_ID);
        name = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.PERIODICAL_NAME);

        titleViewModel = new PeriodicalTitleViewModel(context);
        titleViewModel.imgUrl.set(image);
        titleViewModel.name.set(name);
        titleViewModel.setId(periodicalID);
        items.add(titleViewModel);

        requestServer();
    }

    public PeriodicalDetailViewModel(Context context, String className) {
        super(context);
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
        if (hasNext) {
            mCurrentPage++;
            requestServer();
        }
    }

    @Override
    protected void requestServer() {
        if (!CommonUtil.isNetworkAvailable(context)) {
            setNetworkError();
            return;
        }

        PeriodicalModel.getInstance().getPeriodicalDetail(periodicalID, mCurrentPage, mLastID, new NetworkObserver<SiteDetailBean>() {
            @Override
            public void onSuccess(SiteDetailBean siteDetailBean) {
                refreshing.set(false);
                SiteDetailBean.SiteBean site = siteDetailBean.getSite();
                if (site != null && titleViewModel != null) {
                    titleViewModel.setFollow(site.isFollowed());
                    titleViewModel.name.set(site.getName());
                    titleViewModel.imgUrl.set(site.getImage());
                    titleViewModel.subscribe.set(site.isFollowed() ? ResourceUtil.getString(R.string.periodical_unsubscribe) : ResourceUtil.getString(R.string.periodical_subscribe));
                }
                handleData(siteDetailBean);
                createViewModel();
            }

            @Override
            public void onFailure(String msg) {
                refreshing.set(false);
            }
        });
    }

    /**
     * 处理返回数据
     *
     * @param siteDetailBean 返回数据
     */
    private void handleData(SiteDetailBean siteDetailBean) {
        if (siteDetailBean == null) {
            return;
        }

        hasNext = siteDetailBean.isHas_next();
        List<ArticleDetailBean.ArticleBean> articles = siteDetailBean.getArticles();
        mLastID = articles.get(articles.size() - 1).getId();
        mData.clear();
        mData.addAll(articles);
    }

    @Override
    protected void createViewModel() {
        if (mCurrentPage == 0 && mData.size() == 0) {
            setEmptyPage();
            return;
        }

        for (int i = 0; i < mData.size(); i++) {
            ArticleItemViewModel viewModel = new ArticleItemViewModel(context);
            ArticleDetailBean.ArticleBean articlesBean = mData.get(i);
            viewModel.content.set(articlesBean.getTitle());
            viewModel.date.set(articlesBean.getRectime());
            viewModel.imgUrl.set(articlesBean.getImg());
            viewModel.articleID = articlesBean.getId();
            items.add(viewModel);
        }
    }

    /**
     * 设置空页面
     */
    protected void setEmptyPage() {
        refreshing.set(false);
        if (mBlankVM == null) {
            mBlankVM = new BlankViewModel(context);
        }
        removeExcludeTitle();
        mBlankVM.setStatus(Constants.PageStatus.NO_DATA);
        items.add(mBlankVM);
    }

    /**
     * 设置网络错误页
     */
    protected void setNetworkError() {
        refreshing.set(false);
        if (mBlankVM == null) {
            mBlankVM = new BlankViewModel(context);
        }
        if (mCurrentPage == 0) {
            removeExcludeTitle();
            mBlankVM.setStatus(Constants.PageStatus.NETWORK_EXCEPTION);
            items.add(mBlankVM);
        }
    }

    /**
     * 移除除Title外的item
     */
    protected void removeExcludeTitle() {
        Iterator<BaseViewModel> iterator = items.iterator();
        while (iterator.hasNext()) {
            BaseViewModel viewModel = iterator.next();
            if (Constants.RecyclerItemType.PERIODICAL_TITLE_TYPE != viewModel.getViewType()) {
                iterator.remove();
            }
        }
    }
}
