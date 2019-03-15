package com.minerva.business.category.book;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.business.category.mag.PeriodViewModel;

public class AllBookActivity extends BaseActivity<AllBookViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_all_book_layout;
    }

    @Override
    protected AllBookViewModel getViewModel() {
        return new AllBookViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.allBookVM;
    }
}
