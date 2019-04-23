package com.minerva.business.mine.message;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;

public class MessageActivity extends BaseActivity<MessageViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_message_layout;
    }

    @Override
    protected MessageViewModel getViewModel() {
        return new MessageViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.msgVM;
    }
}
