package com.minerva.business.category.mag;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;

import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.business.category.book.AllBookActivity;
import com.minerva.common.Constants;
import com.minerva.utils.ResourceUtils;

public class SpecialGroupViewModel extends BaseViewModel {
    public ObservableField<String> groupName = new ObservableField<>();
    public ObservableField<String> menuName = new ObservableField<>(ResourceUtils.getString(R.string.special_more));
    public int type;//服务器端的type
    public String tabType;// tab类型,详情见CategoryTabType
    private String id;

    public SpecialGroupViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.SPECIAL_GROUP_TYPE);
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void onMoreClick() {
        switch (tabType) {
            case Constants.CategoryTabType.TAB_MAG:
                Intent magIntent = new Intent(context, MagPeriodActivity.class);
                magIntent.putExtra(Constants.KeyExtra.COLUMN_MAG_TYPE, type);
                magIntent.putExtra(Constants.KeyExtra.COLUMN_MAG_TITLE, groupName.get());
                context.startActivity(magIntent);
                break;
            case Constants.CategoryTabType.TAB_BOOK:
                Intent bookIntent = new Intent(context, AllBookActivity.class);
                bookIntent.putExtra(Constants.KeyExtra.COLUMN_MAG_TYPE, type);
                bookIntent.putExtra(Constants.KeyExtra.COLUMN_MAG_TITLE, groupName.get());
                context.startActivity(bookIntent);
                break;
        }
    }
}
