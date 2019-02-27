package com.minerva.article.detail.model;


import com.minerva.article.list.model.ArticleBean;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArticleDetailModel {
    private static ArticleDetailModel instance;

    private ArticleDetailModel() {
    }

    public static ArticleDetailModel getInstance() {
        if (instance == null) {
            instance = new ArticleDetailModel();
        }
        return instance;
    }

    public void getArticleDetail(String aid, Observer<? super ArticleDetailBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getArticleDetail(aid, 1, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
