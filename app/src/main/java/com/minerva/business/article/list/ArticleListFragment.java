package com.minerva.business.article.list;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseFragment;
import com.minerva.business.article.list.model.ArticleModel;
import com.minerva.common.GlobalData;

public class ArticleListFragment extends BaseFragment<ArticleListViewModel> {
    private ArticleListViewModel articleListViewModel;
    private boolean isRecTab; //标记是否是推荐tab
    private int index; //标记当前的tab

    public void setIndex(int index) {
        this.index = index;
    }

    public void setRecTab(boolean recTab) {
        isRecTab = recTab;
    }

    @Override
    protected ArticleListViewModel getViewModel() {
        if (articleListViewModel == null) {
            articleListViewModel = new ArticleListViewModel(getActivity(), index);
        }

        articleListViewModel.isRecommendGone.set(!isRecTab || GlobalData.getInstance().isLogin());
        return articleListViewModel;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_article_list_layout;
    }

    @Override
    protected int getVariableId() {
        return BR.articleListVM;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ArticleModel.getInstance().onDestroy();
        if (articleListViewModel != null) {
            articleListViewModel.onDetach();
        }
    }
}
