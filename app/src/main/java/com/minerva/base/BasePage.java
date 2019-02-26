package com.minerva.base;

public class BasePage<T> implements MvvmComponent {
    private int mResID;
    private int mVariableID;
    private BaseViewModel mViewModel;
    private T data;

    public BasePage(int resID, int variableID, BaseViewModel viewModel, T data) {
        this.data = data;
        this.mResID = resID;
        this.mVariableID = variableID;
        this.mViewModel = viewModel;
    }

    public T getData() {
        return data;
    }

    @Override
    public int getLayoutResID() {
        return mResID;
    }

    @Override
    public BaseViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public int getVariableID() {
        return mVariableID;
    }
}
