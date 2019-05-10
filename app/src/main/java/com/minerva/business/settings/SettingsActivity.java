package com.minerva.business.settings;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;

public class SettingsActivity extends BaseActivity<SettingsViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_settings_layout;
    }

    @Override
    protected SettingsViewModel getViewModel() {
        return new SettingsViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.settingsVM;
    }
}
