package com.minerva.base;

public interface MvvmComponent<T extends BaseViewModel> {

    int getLayoutResID();

    T getViewModel();

    int getVariableID();
}