package com.minerva.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

/**
 * Created by nayibo on 2018/3/23.
 */

public abstract class BaseActivity<T extends BaseViewModel> extends FragmentActivity {
    protected ViewDataBinding binding;
    private T mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutResID());
        mViewModel = getViewModel();
        binding.setVariable(getVariableID(), mViewModel);
        binding.executePendingBindings();
    }

    protected abstract int getLayoutResID();

    protected abstract T getViewModel();

    protected abstract int getVariableID();

    @Override
    protected void onResume() {
        super.onResume();
        if (mViewModel != null) {
            mViewModel.onResume();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mViewModel != null) {
            mViewModel.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mViewModel != null) {
            mViewModel.onNewIntent(intent);
        }
    }
}
