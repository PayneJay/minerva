package com.minerva.business.mine.signinout;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;

public class RegisterActivity extends BaseActivity<RegisterViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_register_layout;
    }

    @Override
    protected RegisterViewModel getViewModel() {
        return new RegisterViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.registerVM;
    }
}
