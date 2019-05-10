package com.minerva.base;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;

import com.minerva.common.EventMsg;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseViewModel extends BaseObservable implements IViewModel {
    private int mViewType = -1;
    private int itemPosition = -1;
    protected Context context;

    public BaseViewModel(Context context) {
        this.context = context;
    }

    public int getViewType() {
        return mViewType;
    }

    public void setViewType(int mViewType) {
        this.mViewType = mViewType;
    }

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    public final void onViewAttachedToWindow(View view) {
        onAttach();
    }

    @Override
    public void onAttach() {
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onNewIntent(Intent intent) {

    }

    @Override
    public void onVisible(boolean isVisibleToUser) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(EventMsg eventMsg) {
    }

    @Override
    public boolean onKeyDown() {
        return false;
    }

    public final void onViewDetachedFromWindow(View view) {
        onDetach();
    }

}
