package com.minerva.business.mine.message;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;

public class MessageItemViewModel extends BaseViewModel {
    public ObservableInt itemIcon = new ObservableInt();
    public ObservableField<String> itemName = new ObservableField<>();
    private String id;

    MessageItemViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.MY_MESSAGE_ITEM_TYPE);
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
