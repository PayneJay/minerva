package com.minerva.business.mine.user;

import android.content.Intent;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.umeng.socialize.UMShareAPI;

public class UserEditActivity extends BaseActivity<UserEditViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_user_edit_layout;
    }

    @Override
    protected UserEditViewModel getViewModel() {
        return new UserEditViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.userEdtVM;
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
