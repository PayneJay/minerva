package com.minerva.business.category.column;

import android.content.Context;
import android.databinding.ObservableField;

import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;

public class SpecialChildViewModel extends BaseViewModel {
    public ObservableField<String> childName = new ObservableField<>();
    public ObservableField<String> dateText = new ObservableField<>();

    public SpecialChildViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.SPECIAL_CHILD_TYPE);
    }

    public void onItemClick() {
        Constants.showToast(context);
    }
}
