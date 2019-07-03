package com.minerva.business.guide;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.minerva.base.BaseViewModel;

public class GuideItemViewModel extends BaseViewModel {
    public ObservableField<String> name = new ObservableField<>();
    public ObservableBoolean isSelected = new ObservableBoolean();
    public String itemId;

    public GuideItemViewModel(Context context) {
        super(context);
    }

    public void onItemClick() {
        isSelected.set(!isSelected.get());
    }
}
