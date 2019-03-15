package com.minerva.business.category.mag;

import android.content.Context;
import android.content.Intent;

import com.minerva.business.article.detail.ArticleDetailActivity;
import com.minerva.common.Constants;

public class MagChildViewModel extends SpecialChildViewModel {
    public String articleID;

    public MagChildViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.MAG_CHILD_TYPE);
    }

    @Override
    public void onItemClick() {
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(Constants.KeyExtra.ARTICLE_ID, articleID);
        context.startActivity(intent);
    }
}
