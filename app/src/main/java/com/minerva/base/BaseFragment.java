package com.minerva.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment<T extends BaseViewModel> extends Fragment {
    private int mLayoutResId;
    private int mVariableID;
    private T vm;

    protected abstract T getViewModel();
    protected abstract int getLayoutResId();
    protected abstract int getVariableId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vm = getViewModel();
        mVariableID = getVariableId();
        mLayoutResId = getLayoutResId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, mLayoutResId, container, false);
        binding.setVariable(mVariableID, vm);
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (null != vm) {
            vm.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != vm) {
            vm.onPause();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (null != vm) {
            vm.onVisible(isVisibleToUser);
        }
    }
}