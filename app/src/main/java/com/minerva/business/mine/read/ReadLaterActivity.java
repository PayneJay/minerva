package com.minerva.business.mine.read;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;

public class ReadLaterActivity extends BaseActivity<ReadLaterViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_read_later_layout;
    }

    @Override
    protected ReadLaterViewModel getViewModel() {
        return new ReadLaterViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.readLaterVM;
    }
}
