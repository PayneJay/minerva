package com.minerva.business.home.subscribe;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;

public class SubscribeSiteActivity extends BaseActivity<SubscribeViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_subscribe_site_layout;
    }

    @Override
    protected SubscribeViewModel getViewModel() {
        return new SubscribeViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.subscribeVM;
    }
}
