package com.minerva.common;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.utils.ResouceUtils;

public class BlankViewModel extends BaseViewModel {
    public ObservableField<String> description = new ObservableField<>(ResouceUtils.getString(R.string.no_data));
    public ObservableInt drawableRes = new ObservableInt(R.drawable.icon_no_data);

    public BlankViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.BLANK_TYPE);
    }

    public void setStatus(int status) {
        switch (status) {
            case Constants.PageStatus.NO_DATA:
                description.set(ResouceUtils.getString(R.string.no_data));
                drawableRes.set(R.drawable.icon_no_data);
                break;
            case Constants.PageStatus.NETWORK_EXCEPTION:
                description.set(ResouceUtils.getString(R.string.network_error));
                drawableRes.set(R.drawable.icon_network_exception);
                break;
        }
    }
}
