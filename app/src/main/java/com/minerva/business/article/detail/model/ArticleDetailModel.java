package com.minerva.business.article.detail.model;


import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.minerva.MinervaApp;
import com.minerva.base.BaseBean;
import com.minerva.common.Constants;
import com.minerva.db.Article;
import com.minerva.db.ArticleDao;
import com.minerva.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 获取文章详情
     *
     * @param aid      文章id
     * @param observer 回调
     */
    public void getArticleDetail(String aid, Observer<? super ArticleDetailBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getArticleDetail(aid, 1, 2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 添加待读(登录状态下)
     *
     * @param aid      文章id
     * @param observer 回调
     */
    public void markReadLate(String aid, Observer<? super BaseBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .markReadLate(aid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 取消待读
     *
     * @param aid      文章id
     * @param observer 回调
     */
    public void cancelReadLate(String aid, Observer<? super BaseBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .cancelReadLate(aid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 添加到阅读历史
     *
     * @param article 文章
     */
    public void addReadHistory(ArticleDetailBean.ArticleBean article) {
        ArticleDao articleDao = ((MinervaApp) Constants.application).getDaoSession().getArticleDao();
        Article articleQuery = queryById(article.getId(), 1);
        if (articleQuery == null) {
            Article articleNew = convertArticle(article, 1);
            articleDao.insert(articleNew);
        }
    }

    /**
     * 添加到待读列表
     *
     * @param article 文章
     */
    public void addUnRead(ArticleDetailBean.ArticleBean article) {
        ArticleDao articleDao = ((MinervaApp) Constants.application).getDaoSession().getArticleDao();
        Article articleQuery = queryById(article.getId(), 0);
        if (null == articleQuery) {
            Article articleNew = convertArticle(article, 0);
            articleDao.insert(articleNew);
        }
    }

    /**
     * 取消待读
     *
     * @param id 文章id
     */
    public void unMarkUnReadById(String id) {
        ArticleDao articleDao = ((MinervaApp) Constants.application).getDaoSession().getArticleDao();
        Article article = queryById(id, 0);
        if (null != article) {
            articleDao.delete(article);
        }
    }

    /**
     * 通过id和type查找
     *
     * @return
     */
    public Article queryById(String id, int type) {
        ArticleDao articleDao = ((MinervaApp) Constants.application).getDaoSession().getArticleDao();
        List<Article> articles = new ArrayList<>();
        try {
            articles = articleDao.queryBuilder()
                    .where(ArticleDao.Properties.Aid.eq(id), ArticleDao.Properties.Type.eq(type))
                    .build()
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Article article : articles) {
            if (TextUtils.equals(id, article.getAid())) {
                return article;
            }
        }
        return null;
    }

    /**
     * 根据类型查找所有
     *
     * @param type 类型
     * @return
     */
    public List<Article> loadAllByType(int type) {
        ArticleDao articleDao = ((MinervaApp) Constants.application).getDaoSession().getArticleDao();
        List<Article> articles = new ArrayList<>();
        try {
            articles = articleDao.queryBuilder()
                    .where(ArticleDao.Properties.Type.eq(type))
                    .orderDesc(ArticleDao.Properties.Timestamp)
                    .build()
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return articles;
    }

    @NonNull
    private Article convertArticle(ArticleDetailBean.ArticleBean article, int type) {
        Article articleNew = new Article();
        articleNew.setTimestamp(System.currentTimeMillis());
        articleNew.setAid(article.getId());
        articleNew.setTitle(article.getTitle());
        articleNew.setFeed_title(article.getFeed_title());
        articleNew.setTime(article.getTime());
        articleNew.setImg(article.getImg());
        articleNew.setType(type);
        return articleNew;
    }

}
