package com.minerva.common.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;

import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;
import com.minerva.utils.ResourceUtil;

public class NoMoreViewModel extends BaseViewModel {
    public ObservableField<String> desc = new ObservableField<>();

    public NoMoreViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.NO_MORE_ITEM_TYPE);
        desc.set(ResourceUtil.getString(R.string.no_more_data));
    }
}
