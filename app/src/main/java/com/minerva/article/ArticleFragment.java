package com.minerva.article;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseFragment;

public class ArticleFragment extends BaseFragment<ArticleViewModel> {
    private ArticleViewModel articleViewModel;

    @Override
    protected ArticleViewModel getViewModel() {
        if (articleViewModel == null) {
            articleViewModel = new ArticleViewModel(getActivity());
        }
        articleViewModel.fragment.set(this);
        return articleViewModel;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_article_layout;
    }

    @Override
    protected int getVariableId() {
        return BR.articleVM;
    }
}
