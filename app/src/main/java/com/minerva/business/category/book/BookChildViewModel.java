package com.minerva.business.category.book;

import android.content.Context;
import android.databinding.ObservableField;

import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;

public class BookChildViewModel extends BaseViewModel {
    public ObservableField<String> childName = new ObservableField<>();
    public ObservableField<String> imgUrl = new ObservableField<>("https://img11.360buyimg.com/n1/jfs/t1/18778/3/2645/244256/5c204ba9E06f47ed4/09b48b768a139623.jpg");

    public BookChildViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.SPECIAL_CHILD_TYPE);
    }

    public void onItemClick() {
        Constants.showToast(context);
    }
}
