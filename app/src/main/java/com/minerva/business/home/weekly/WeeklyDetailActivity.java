package com.minerva.business.home.weekly;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;

public class WeeklyDetailActivity extends BaseActivity<WeeklyDetailViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_weekly_detail_layout;
    }

    @Override
    protected WeeklyDetailViewModel getViewModel() {
        return new WeeklyDetailViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.weeklyDetailVM;
    }
}
