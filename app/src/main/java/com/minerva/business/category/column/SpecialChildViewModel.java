package com.minerva.business.category.column;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;

import com.minerva.base.BaseViewModel;
import com.minerva.business.article.detail.ArticleDetailActivity;
import com.minerva.common.Constants;

public class SpecialChildViewModel extends BaseViewModel {
    public ObservableField<String> childName = new ObservableField<>();
    public ObservableField<String> dateText = new ObservableField<>();
    public String magID, groupName;
    public int type;

    public SpecialChildViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.SPECIAL_CHILD_TYPE);
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
