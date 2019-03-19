package com.minerva.business.search;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.business.article.list.ArticleItemViewModel;
import com.minerva.business.article.list.model.ArticleBean;
import com.minerva.business.category.book.AllBook;
import com.minerva.business.category.model.BookBean;
import com.minerva.business.search.model.ArticleResult;
import com.minerva.business.search.model.SearchModel;
import com.minerva.business.search.model.SiteResult;
import com.minerva.common.Constants;
import com.minerva.common.EventMsg;
import com.minerva.network.NetworkObserver;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class SearchListViewModel extends BaseViewModel {
    public ObservableBoolean refreshing = new ObservableBoolean();
    public ObservableList<BaseViewModel> items = new ObservableArrayList<>();
    public OnItemBind<BaseViewModel> resultItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            switch (item.getViewType()) {
                case Constants.RecyclerItemType.ARTICLE_COMMON_TYPE:
                    itemBinding.set(BR.articleItemVM, R.layout.item_article_common_layout);
                    break;
                case Constants.RecyclerItemType.SEARCH_SITE_TYPE:
                    itemBinding.set(BR.resultSiteVM, R.layout.item_search_site_layout);
                    break;
                case Constants.RecyclerItemType.SEARCH_BOOK_TYPE:
                    itemBinding.set(BR.resultBookVM, R.layout.item_search_book_layout);
                    break;
                case Constants.RecyclerItemType.SEARCH_KW_HISTORY_TYPE:
                    itemBinding.set(BR.searchHistoryVM, R.layout.item_search_history_layout);
                    break;
                case Constants.RecyclerItemType.BLANK_TYPE:
                    itemBinding.set(BR.vmBlank, R.layout.item_blank_layout);
                    break;
            }
        }
    };
    private List<ArticleBean.ArticlesBean> mArticleData = new ArrayList<>();
    private List<SiteResult.ResultItem> mSiteData = new ArrayList<>();
    private List<BookBean.ItemsBean.BooksBean> mBookData = new ArrayList<>();
    private String keyWord;
    private int mCurrentTab; //当前Tab
    private int mCurrentPage; //当前页数
    private boolean hasNext;

    SearchListViewModel(Context context, int tab) {
        super(context);
        this.mCurrentTab = tab;
        EventBus.getDefault().register(this);
        setSearchHistory();
    }

    public int[] getColors() {
        return new int[]{R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark};
    }

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mCurrentPage = 0;
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

    @Override
    public void onEvent(EventMsg eventMsg) {
        super.onEvent(eventMsg);
        switch (eventMsg.getMsg()) {
            case Constants.EventMsgKey.QUERY_SUBMITTED:
                if (TextUtils.equals(keyWord, eventMsg.getContent())) {
                    return;
                }

                keyWord = eventMsg.getContent();
                requestServer();
                break;
            case Constants.EventMsgKey.DELETE_SEARCH_KEYWORD:
                setSearchHistory();
                break;
        }
    }

    private void requestServer() {
        switch (mCurrentTab) {
            case 0://搜索文章
                searchArticle();
                break;
            case 1://搜索站点
                searchSite();
                break;
            case 2://搜索图书
                searchBook();
                break;
        }
    }

    private void searchArticle() {
        SearchModel.getInstance().searchArticleByKW(mCurrentPage, keyWord, new NetworkObserver<ArticleResult>() {
            @Override
            public void onSuccess(ArticleResult articleResult) {
                refreshing.set(false);

                hasNext = articleResult.isHas_next();
                mCurrentPage = articleResult.getPn();
                List<ArticleBean.ArticlesBean> articles = articleResult.getArticles();
                if (articles.size() > 0) {
                    mArticleData.clear();
                    mArticleData.addAll(articles);
                    createArticleItemViewModel();
                }
            }

            @Override
            public void onFailure() {
                refreshing.set(false);
            }
        });
    }

    private void searchSite() {
        SearchModel.getInstance().searchSiteByKW(keyWord, new NetworkObserver<SiteResult>() {
            @Override
            public void onSuccess(SiteResult siteResult) {
                refreshing.set(false);

                List<SiteResult.ResultItem> items = siteResult.getItems();
                if (items.size() > 0) {
                    mSiteData.clear();
                    mSiteData.addAll(items);
                    createSiteItemViewModel();
                }
            }

            @Override
            public void onFailure() {
                refreshing.set(false);
            }
        });
    }

    private void searchBook() {
        SearchModel.getInstance().searchBookByKW(mCurrentPage, keyWord, new NetworkObserver<AllBook>() {
            @Override
            public void onSuccess(AllBook allBook) {
                refreshing.set(false);
                hasNext = allBook.isHas_next();

                List<BookBean.ItemsBean.BooksBean> items = allBook.getItems();
                if (items.size() > 0) {
                    mBookData.clear();
                    mBookData.addAll(items);
                    createBookItemViewModel();
                }
            }

            @Override
            public void onFailure() {
                refreshing.set(false);
            }
        });
    }

    private void createArticleItemViewModel() {
        if (mCurrentPage == 0) {
            items.clear();
        }

        for (ArticleBean.ArticlesBean articlesBean : mArticleData) {
            ArticleItemViewModel viewModel = new ArticleItemViewModel(context);
            viewModel.content.set(articlesBean.getTitle());
            viewModel.date.set(articlesBean.getRectime());
            viewModel.imgUrl.set(articlesBean.getImg());
            viewModel.articleID = articlesBean.getId();
            items.add(viewModel);
        }
    }

    private void createSiteItemViewModel() {
        if (mCurrentPage == 0) {
            items.clear();
        }

        for (SiteResult.ResultItem siteResult : mSiteData) {
            ResultSiteViewModel viewModel = new ResultSiteViewModel(context);
            viewModel.imgUrl.set(siteResult.getImage());
            viewModel.name.set(siteResult.getName());
            viewModel.id = siteResult.getId();
            items.add(viewModel);
        }
    }

    private void createBookItemViewModel() {
        if (mCurrentPage == 0) {
            items.clear();
        }

        for (BookBean.ItemsBean.BooksBean childItem : mBookData) {
            ResultBookViewModel childViewModel = new ResultBookViewModel(context);
            childViewModel.childName.set(childItem.getTitle());
            childViewModel.imgUrl.set(childItem.getThumb());
            childViewModel.author.set(childItem.getAuthor());
            childViewModel.link = childItem.getLink();
            items.add(childViewModel);
        }
    }

    /**
     * 设置显示搜索历史
     */
    private void setSearchHistory() {
        List<String> searchHistory = SearchModel.getInstance().getSearchHistory(context);
        items.clear();
        for (String keyword : searchHistory) {
            SearchHistoryViewModel viewModel = new SearchHistoryViewModel(context);
            viewModel.keyword.set(keyword);
            items.add(viewModel);
        }
    }
}
