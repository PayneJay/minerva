package com.minerva.business.article.list;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;

import com.minerva.business.article.detail.ArticleDetailActivity;
import com.minerva.common.Constants;
import com.minerva.base.BaseViewModel;

public class ArticleItemViewModel extends BaseViewModel {
    public ObservableField<String> content = new ObservableField<>("前线 | Apple Music或入驻谷歌智能音箱，为苹果打开服务版图");
    public ObservableField<String> date = new ObservableField<>("36氪 02-27 15:16");
    public ObservableField<String> imgUrl = new ObservableField<>("https://aimg2.tuicool.com/uAnYBrY.jpg");

    public ArticleItemViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.ARTICLE_COMMON_TYPE);
    }

    public void viewDetail(){
        Intent intent = new Intent(context, ArticleDetailActivity.class);
        intent.putExtra(Constants.KeyExtra.ARTICLE_ID, "yUfAV3F");
        context.startActivity(intent);
    }
}
