package com.minerva.business.home.weekly;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;
import com.minerva.business.home.weekly.model.WeeklyModel;

public class WeeklyActivity extends BaseActivity<WeeklyViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_weekly_layout;
    }

    @Override
    protected WeeklyViewModel getViewModel() {
        return new WeeklyViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.weeklyVM;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WeeklyModel.getInstance().onDestroy();
    }
}
