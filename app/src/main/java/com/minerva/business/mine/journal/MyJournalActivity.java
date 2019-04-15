package com.minerva.business.mine.journal;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;

public class MyJournalActivity extends BaseActivity<MyJournalViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_my_journal_layout;
    }

    @Override
    protected MyJournalViewModel getViewModel() {
        return new MyJournalViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.journalVM;
    }
}
