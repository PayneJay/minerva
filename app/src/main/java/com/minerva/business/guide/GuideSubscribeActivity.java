package com.minerva.business.guide;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;

public class GuideSubscribeActivity extends BaseActivity<GuideSubscribeViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_subscribe_guide_layout;
    }

    @Override
    protected GuideSubscribeViewModel getViewModel() {
        return new GuideSubscribeViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.guideVM;
    }
}
