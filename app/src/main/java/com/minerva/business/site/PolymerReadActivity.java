package com.minerva.business.site;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;

public class PolymerReadActivity extends BaseActivity<PolymerReadViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_polymer_read_layout;
    }

    @Override
    protected PolymerReadViewModel getViewModel() {
        return new PolymerReadViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.polymerizeVM;
    }
}
