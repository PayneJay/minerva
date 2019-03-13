package com.minerva.business.category.column;

import android.content.Context;
import android.databinding.ObservableField;

import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;

public class MagTitleViewModel extends BaseViewModel {
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> title = new ObservableField<>();

    public MagTitleViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.MAG_TITLE_TYPE);
    }
}
