package com.minerva.business.category.book;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;

import com.minerva.base.BaseViewModel;
import com.minerva.business.article.detail.ArticleDetailActivity;
import com.minerva.common.Constants;

public class BookChildViewModel extends BaseViewModel {
    public ObservableField<String> childName = new ObservableField<>();
    public ObservableField<String> imgUrl = new ObservableField<>();
    public String link;

    public BookChildViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.SPECIAL_CHILD_TYPE);
    }

    public void onItemClick() {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(Constants.KeyExtra.BOOK_JD_LINK, link);
        context.startActivity(intent);
    }
}
