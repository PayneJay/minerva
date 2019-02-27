package com.minerva.article;

import com.minerva.R;
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
        return 0;
    }
}
