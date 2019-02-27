package com.minerva.article.detail;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;

public class ArticleDetailActivity extends BaseActivity<ArticleDetailViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_article_detail_layout;
    }

    @Override
    protected ArticleDetailViewModel getViewModel() {
        return new ArticleDetailViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.articleDetailVM;
    }
}
