package com.minerva.business.category.mag;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;

import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;
import com.minerva.utils.DateUtils;
import com.minerva.utils.ResourceUtils;

import java.util.Date;

public class SpecialChildViewModel extends BaseViewModel {
    public ObservableField<String> childName = new ObservableField<>();
    public ObservableField<String> dateText = new ObservableField<>();
    public String magID, groupName;
    public int type;

    public SpecialChildViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.SPECIAL_CHILD_TYPE);
    }

    public void setDate(long time) {
        if (DateUtils.isToday(time)) {
            dateText.set(ResourceUtils.getString(R.string.update_today));
            return;
        }
        dateText.set(DateUtils.date2Str(new Date(time), "MM月dd日"));
    }

    public void onItemClick() {
        Intent intent = new Intent(context, MagDetailActivity.class);
        intent.putExtra(Constants.KeyExtra.COLUMN_MAG_ID, magID);
        intent.putExtra(Constants.KeyExtra.COLUMN_MAG_TYPE, type);
        intent.putExtra(Constants.KeyExtra.COLUMN_MAG_TITLE, groupName);
        intent.putExtra(Constants.KeyExtra.COLUMN_MAG_NUMBER, childName.get());
        context.startActivity(intent);
    }
}
