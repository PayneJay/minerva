package com.minerva.business.special.column;

import android.content.Context;
import android.databinding.ObservableField;

import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;

public class SpecialGroupViewModel extends BaseViewModel {
    public ObservableField<String> groupName = new ObservableField<>();

    public SpecialGroupViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.SPECIAL_GROUP_TYPE);
    }

    public void onMoreClick() {
        Constants.showToast(context);
    }
}
