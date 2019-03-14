package com.minerva.business.mine.user;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;

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
}
