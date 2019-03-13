package com.minerva.business.category.column;

import android.content.Context;
import android.databinding.ObservableField;

import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;
import com.minerva.utils.ResouceUtils;

public class SpecialGroupViewModel extends BaseViewModel {
    public ObservableField<String> groupName = new ObservableField<>();
    public ObservableField<String> menuName = new ObservableField<>(ResouceUtils.getString(R.string.special_more));

    public SpecialGroupViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.SPECIAL_GROUP_TYPE);
    }

    public void onMoreClick() {
        Constants.showToast(context);
    }
}
