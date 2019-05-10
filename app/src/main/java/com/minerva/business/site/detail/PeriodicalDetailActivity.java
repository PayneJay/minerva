package com.minerva.business.site.detail;

import android.view.KeyEvent;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseBean;

import org.greenrobot.eventbus.EventBus;

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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
