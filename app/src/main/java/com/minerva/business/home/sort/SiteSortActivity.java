package com.minerva.business.home.sort;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;

public class SiteSortActivity extends BaseActivity<SiteSortViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_site_sort_layout;
    }

    @Override
    protected SiteSortViewModel getViewModel() {
        return new SiteSortViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.sortVM;
    }
}
