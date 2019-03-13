package com.minerva.business.category.column.model;

import android.content.Context;

import com.minerva.business.category.column.SpecialChildViewModel;
import com.minerva.common.Constants;

public class MagChildViewModel extends SpecialChildViewModel {
    public MagChildViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.MAG_CHILD_TYPE);
    }

    @Override
    public void onItemClick() {
        Constants.showToast(context);
    }
}
