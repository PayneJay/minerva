package com.minerva.business.article.list.model;

import com.minerva.business.category.model.BookBean;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;
import com.minerva.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArticleModel {
    private static ArticleModel instance;

    private ArticleModel() {
    }

    public static ArticleModel getInstance() {
        if (instance == null) {
            instance = new ArticleModel();
        }
        return instance;
    }

    public void getArticleList(String cid, int lang, Observer<? super ArticleBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getHotArticles(30, lang, cid, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public List<ArticleBean.ArticlesBean> generateArticlesData() {
        ArticleBean bean = CommonUtils.getArticleListFromJson();
        List<ArticleBean.ArticlesBean> list = new ArrayList<>();
        list.addAll(bean.getArticles());
        return list;
    }
}
