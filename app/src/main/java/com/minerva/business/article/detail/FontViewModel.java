package com.minerva.business.article.detail;

import android.content.Context;
import android.databinding.ObservableBoolean;

import com.minerva.base.BaseViewModel;

public class FontViewModel extends BaseViewModel {
    public ObservableBoolean isSmallSelected = new ObservableBoolean(false);
    public ObservableBoolean isMiddleSelected = new ObservableBoolean(true);
    public ObservableBoolean isBigSelected = new ObservableBoolean(false);
    private IFontSelectedListener listener;

    FontViewModel(Context context, IFontSelectedListener listener) {
        super(context);
        this.listener = listener;
    }

    public void onSmallClick() {
        isSmallSelected.set(true);
        isMiddleSelected.set(false);
        isBigSelected.set(false);
        if (listener != null) {
            listener.onSmallClick();
        }
    }

    public void onMiddleClick() {
        isSmallSelected.set(false);
        isMiddleSelected.set(true);
        isBigSelected.set(false);
        if (listener != null) {
            listener.onMiddleClick();
        }
    }

    public void onBigClick() {
        isSmallSelected.set(false);
        isMiddleSelected.set(false);
        isBigSelected.set(true);
        if (listener != null) {
            listener.onBigClick();
        }
    }
}
