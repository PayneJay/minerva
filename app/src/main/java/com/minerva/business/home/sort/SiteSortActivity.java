package com.minerva.business.home.sort;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;

public class SiteSortActivity extends BaseActivity<SiteSortViewModel> {
    private SiteSortViewModel siteSortViewModel;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_site_sort_layout;
    }

    @Override
    protected SiteSortViewModel getViewModel() {
        if (siteSortViewModel == null) {
            siteSortViewModel = new SiteSortViewModel(this);
        }
        return siteSortViewModel;
    }

    @Override
    protected int getVariableID() {
        return BR.sortVM;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (siteSortViewModel != null) {
            siteSortViewModel.onDetach();
        }
    }
}
