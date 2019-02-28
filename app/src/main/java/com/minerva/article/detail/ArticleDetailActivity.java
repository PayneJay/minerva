package com.minerva.article.detail;

import android.content.Intent;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;
import com.umeng.socialize.UMShareAPI;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
