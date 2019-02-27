package com.minerva.article.list;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseFragment;

public class ArticleListFragment extends BaseFragment<ArticleListViewModel> {
    @Override
    protected ArticleListViewModel getViewModel() {
        return new ArticleListViewModel(getActivity());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_article_list_layout;
    }

    @Override
    protected int getVariableId() {
        return BR.articleListVM;
    }
}
