package com.minerva.business.site.detail;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;

public class PeriodicalDetailActivity extends BaseActivity<PeriodicalDetailViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_periodical_detail_layout;
    }

    @Override
    protected PeriodicalDetailViewModel getViewModel() {
        return new PeriodicalDetailViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.periodicalVM;
    }
}
