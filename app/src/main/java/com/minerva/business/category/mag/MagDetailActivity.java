package com.minerva.business.category.mag;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;

public class MagDetailActivity extends BaseActivity<MagDetailViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_mag_detail_layout;
    }

    @Override
    protected MagDetailViewModel getViewModel() {
        return new MagDetailViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.magVM;
    }
}
