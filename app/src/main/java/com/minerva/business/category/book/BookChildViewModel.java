package com.minerva.business.category.book;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;

import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;
import com.minerva.common.WebViewActivity;
import com.minerva.utils.ResourceUtil;

public class BookChildViewModel extends BaseViewModel {
    public ObservableField<String> childName = new ObservableField<>();
    public ObservableField<String> imgUrl = new ObservableField<>();
    public String link;
    private String id;

    public BookChildViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.SPECIAL_CHILD_TYPE);
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void onItemClick() {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(Constants.KeyExtra.WEB_URL_LINK, link);
        intent.putExtra(Constants.KeyExtra.WEB_VIEW_TITLE, ResourceUtil.getString(R.string.jd_book_store));
        context.startActivity(intent);
    }
}
