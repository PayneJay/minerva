package com.minerva.business.mine.loginregister;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;

public class LoginActivity extends BaseActivity<LoginViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_login_layout;
    }

    @Override
    protected LoginViewModel getViewModel() {
        return new LoginViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.loginVM;
    }
}
