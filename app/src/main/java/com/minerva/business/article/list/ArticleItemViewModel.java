package com.minerva.business.article.list;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.minerva.business.article.detail.ArticleDetailActivity;
import com.minerva.common.Constants;
import com.minerva.base.BaseViewModel;

public class ArticleItemViewModel extends BaseViewModel {
    public ObservableField<String> content = new ObservableField<>();
    public ObservableField<String> date = new ObservableField<>();
    public ObservableField<String> imgUrl = new ObservableField<>();
    public ObservableBoolean isHotFlagGone = new ObservableBoolean(true);
    public String articleID;

    public ArticleItemViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.ARTICLE_COMMON_TYPE);
    }

    public void viewDetail() {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(Constants.KeyExtra.ARTICLE_ID, articleID);
        context.startActivity(intent);
    }
}
