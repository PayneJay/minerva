package com.minerva.article;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseFragment;

public class ArticleFragment extends BaseFragment<ArticleViewModel> {
    @Override
    protected ArticleViewModel getViewModel() {
        return new ArticleViewModel();
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
