package com.minerva.business.mine.signinout;

import android.content.Intent;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.umeng.socialize.UMShareAPI;

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
