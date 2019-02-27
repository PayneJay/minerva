package com.minerva.article;

import android.content.Context;
import android.databinding.ObservableField;

import com.minerva.base.BaseViewModel;

public class ArticleListViewModel extends BaseViewModel {
    public ObservableField<String> content = new ObservableField<>();

    public ArticleListViewModel(Context context) {
        super(context);
    }
}
