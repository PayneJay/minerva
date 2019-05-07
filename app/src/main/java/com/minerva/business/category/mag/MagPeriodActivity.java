package com.minerva.business.category.mag;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.business.category.mag.model.MagListModel;

public class MagPeriodActivity extends BaseActivity<PeriodViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_mag_period_layout;
    }

    @Override
    protected PeriodViewModel getViewModel() {
        return new PeriodViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.periodVM;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MagListModel.getInstance().onDestroy();
    }
}
