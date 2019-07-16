package com.minerva.business.article.list;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.business.article.detail.model.ArticleDetailBean;
import com.minerva.business.article.list.model.ArticleListBean;
import com.minerva.business.article.list.model.ArticleModel;
import com.minerva.business.mine.signinout.LoginActivity;
import com.minerva.common.BlankViewModel;
import com.minerva.common.viewmodel.RefreshListViewModel;
import com.minerva.common.Constants;
import com.minerva.common.EventMsg;
import com.minerva.common.viewmodel.NoMoreViewModel;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class ArticleListViewModel extends RefreshListViewModel {
    public ObservableBoolean isRecommendGone = new ObservableBoolean();
    public ObservableList<BaseViewModel> items = new ObservableArrayList<>();
    public OnItemBind<BaseViewModel> articleItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            switch (item.getViewType()) {
                case Constants.RecyclerItemType.ARTICLE_COMMON_TYPE:
                    itemBinding.set(BR.articleItemVM, R.layout.item_article_common_layout);
                    break;
                case Constants.RecyclerItemType.NO_MORE_ITEM_TYPE:
                    itemBinding.set(BR.noMoreVM, R.layout.item_no_more_layout);
                    break;
                case Constants.RecyclerItemType.BLANK_TYPE:
                    itemBinding.set(BR.vmBlank, R.layout.item_blank_layout);
                    break;
            }
        }
    };
    protected BlankViewModel mBlankVM;
    protected List<ArticleDetailBean.ArticleBean> mData = new ArrayList<>();
    protected boolean hasNext;
    protected int mCurrentPage; //当前页数
    private String mLastID; //最后一条id
    private int mCurrentTab; //当前Tab

    ArticleListViewModel(Context context, int tab) {
        super(context);
        this.mCurrentTab = tab;
        EventBus.getDefault().register(this);
        refreshing.set(true);
        requestServer();
    }

    public ArticleListViewModel(Context context, String className) {
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
        switch (eventMsg.getMsg()) {
            case Constants.EventMsgKey.LOGIN_SUCCESS:
                requestServer();
                break;
            case Constants.EventMsgKey.SELECT_ARTICLE_LANGUAGE:
                Map<String, Integer> language = ArticleModel.getInstance().getLanguageMap();
                language.put(String.valueOf(mCurrentTab), eventMsg.getIndex());
                ArticleModel.getInstance().setLanguageMap(language);
                mCurrentPage = 0;
                mLastID = "";
                requestServer();
                break;
        }
    }

    protected void createViewModel() {
        if (mBlankVM == null) {
            mBlankVM = new BlankViewModel(context);
        }

        if (mCurrentPage == 0) {
            items.clear();
        }
        if (mData.size() > 0) {
            for (ArticleDetailBean.ArticleBean articleBean : mData) {
                ArticleItemViewModel viewModel = new ArticleItemViewModel(context);
                viewModel.content.set(articleBean.getTitle());
                viewModel.date.set(articleBean.getFeed_title() + "  " + articleBean.getRectime());
                viewModel.imgUrl.set(articleBean.getImg());
                viewModel.isHotFlagGone.set(articleBean.getSt() != 2);
                viewModel.articleID = articleBean.getId();
                items.add(viewModel);
            }

            if (!hasNext && items.size() > 10) {
                items.add(new NoMoreViewModel(context));
            }
        } else {
            items.clear();
            mBlankVM.setStatus(Constants.PageStatus.NO_DATA);
            items.add(mBlankVM);
        }
    }

    protected void requestServer() {
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

        ArticleModel.getInstance().getArticleList(mCurrentTab, getLanguage(), mLastID, mCurrentPage,
                new IUnLoginListener() {
                    @Override
                    public void unLogin() {
                        refreshing.set(false);
                    }
                }, new NetworkObserver<ArticleListBean>() {
                    @Override
                    public void onSuccess(ArticleListBean articleListBean) {
                        refreshing.set(false);
                        isRecommendGone.set(true);
                        handleData(articleListBean);
                        createViewModel();
                    }

                    @Override
                    public void onFailure(String msg) {
                        refreshing.set(false);
                        createViewModel();
                    }
                });
    }

    private int getLanguage() {
        Map<String, Integer> language = ArticleModel.getInstance().getLanguageMap();
        return language.get(String.valueOf(mCurrentTab));
    }

    /**
     * 处理返回数据
     *
     * @param articleListBean 返回数据
     */
    protected void handleData(ArticleListBean articleListBean) {
        mData.clear();
        if (articleListBean == null || articleListBean.getArticles().size() <= 0) {
            return;
        }

        List<ArticleDetailBean.ArticleBean> articles = articleListBean.getArticles();
        mCurrentPage = articleListBean.getPn();
        hasNext = articleListBean.isHas_next();
        mLastID = articles.get(articles.size() - 1).getId();
        mData.addAll(articles);
    }
}
