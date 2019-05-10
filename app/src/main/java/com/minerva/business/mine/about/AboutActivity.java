package com.minerva.business.mine.about;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;

public class AboutActivity extends BaseActivity<AboutViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_about_layout;
    }

    @Override
    protected AboutViewModel getViewModel() {
        return new AboutViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.aboutVM;
    }
}
