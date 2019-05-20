package com.minerva.common;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;

public class WebViewActivity extends BaseActivity<WebViewViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_web_view_layout;
    }

    @Override
    protected WebViewViewModel getViewModel() {
        return new WebViewViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.webViewVM;
    }
}
