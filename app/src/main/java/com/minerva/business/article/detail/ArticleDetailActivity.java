package com.minerva.business.article.detail;

import android.content.Intent;
import android.content.res.Configuration;

import com.iflytek.sunflower.FlowerCollector;
import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.umeng.socialize.UMShareAPI;

public class ArticleDetailActivity extends BaseActivity<ArticleDetailViewModel> {
    private ArticleDetailViewModel detailViewModel;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_article_detail_layout;
    }

    @Override
    protected ArticleDetailViewModel getViewModel() {
        if (detailViewModel == null) {
            detailViewModel = new ArticleDetailViewModel(this);
        }
        return detailViewModel;
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

    /**
     * 屏幕横竖屏切换时避免出现window leak的问题
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (detailViewModel != null) {
            detailViewModel.closeShareAction();
        }
    }

    @Override
    protected void onResume() {
        //移动数据统计分析
        FlowerCollector.onResume(this);
        FlowerCollector.onPageStart(getLocalClassName());
        super.onResume();
    }

    @Override
    protected void onPause() {
        //移动数据统计分析
        FlowerCollector.onPageEnd(getLocalClassName());
        FlowerCollector.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
        if (detailViewModel != null) {
            detailViewModel.onDetach();
        }
    }
}
