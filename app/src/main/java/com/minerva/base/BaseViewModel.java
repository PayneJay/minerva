package com.minerva.base;

import android.content.Intent;
import android.databinding.BaseObservable;
import android.view.View;


/**
 * Created by nayibo on 2018/1/24.
 */

public abstract class BaseViewModel extends BaseObservable implements IViewModel {
    private int mViewType = -1;
    private int itemPosition = -1;

    public BaseViewModel() {
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

    }

    @Override
    public void onDetach() {
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

    @Override
    public boolean onKeyDown() {
        return false;
    }

    public final void onViewDetachedFromWindow(View view) {
        onDetach();
    }

}
