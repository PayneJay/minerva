package com.minerva.business.search;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;

import com.minerva.business.category.book.BookChildViewModel;
import com.minerva.business.category.book.BookDetailActivity;
import com.minerva.common.Constants;

public class ResultBookViewModel extends BookChildViewModel {
    public ObservableField<String> author = new ObservableField<>();
    public String link;

    ResultBookViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.SEARCH_BOOK_TYPE);
    }

    public void onItemClick() {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(Constants.KeyExtra.BOOK_JD_LINK, link);
        context.startActivity(intent);
    }
}
