package com.minerva.business.home.subscribe;

import android.view.KeyEvent;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseBean;

import org.greenrobot.eventbus.EventBus;

public class SubscribeSiteActivity extends BaseActivity<SubscribeViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_subscribe_site_layout;
    }

    @Override
    protected SubscribeViewModel getViewModel() {
        return new SubscribeViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.subscribeVM;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SubscribeModel.getInstance().onDestroy();
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
        BaseBean baseBean = new BaseBean();
        baseBean.setSuccess(true);
        EventBus.getDefault().postSticky(baseBean);
        super.finish();
    }
}
