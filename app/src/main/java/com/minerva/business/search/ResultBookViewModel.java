package com.minerva.business.search;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;

import com.minerva.R;
import com.minerva.business.category.book.BookChildViewModel;
import com.minerva.common.WebViewActivity;
import com.minerva.common.Constants;
import com.minerva.utils.ResourceUtil;

public class ResultBookViewModel extends BookChildViewModel {
    public ObservableField<String> author = new ObservableField<>();
    public String link;

    ResultBookViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.SEARCH_BOOK_TYPE);
    }

    public void onItemClick() {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra(Constants.KeyExtra.WEB_URL_LINK, link);
        intent.putExtra(Constants.KeyExtra.WEB_VIEW_TITLE, ResourceUtil.getString(R.string.jd_book_store));
        context.startActivity(intent);
    }
}
