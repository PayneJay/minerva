package com.minerva.business.article.detail.model;


import android.support.annotation.NonNull;

import com.minerva.MinervaApp;
import com.minerva.base.BaseBean;
import com.minerva.common.Constants;
import com.minerva.db.Article;
import com.minerva.db.ArticleDao;
import com.minerva.network.RetrofitHelper;

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
        Article articleNew = getArticle(article);
        articleNew.setType(1);
        Article articleQuery = queryById(article.getId());
        if (articleQuery == null) {
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
        Article articleNew = getArticle(article);
        articleNew.setType(0);
        articleDao.insertOrReplace(articleNew);
    }

    /**
     * 取消待读
     *
     * @param id 文章id
     */
    public void unMarkUnReadById(String id) {
        ArticleDao articleDao = ((MinervaApp) Constants.application).getDaoSession().getArticleDao();
        Article article = queryById(id);
        if (null != article) {
            articleDao.delete(article);
        }
    }

    /**
     * 通过id查找
     *
     * @return
     */
    public Article queryById(String id) {
        ArticleDao articleDao = ((MinervaApp) Constants.application).getDaoSession().getArticleDao();
        Article article = articleDao.queryBuilder()
                .where(ArticleDao.Properties.Aid.eq(id), ArticleDao.Properties.Type.eq(0))
                .build()
                .unique();
        return article;
    }

    /**
     * 根据类型查找所有
     *
     * @param type 类型
     * @return
     */
    public List<Article> loadAllByType(int type) {
        ArticleDao articleDao = ((MinervaApp) Constants.application).getDaoSession().getArticleDao();
        List<Article> articles = articleDao.queryBuilder()
                .where(ArticleDao.Properties.Type.eq(type))
                .orderDesc(ArticleDao.Properties.Timestamp)
                .build()
                .list();
        return articles;
    }

    @NonNull
    private Article getArticle(ArticleDetailBean.ArticleBean article) {
        Article articleNew = new Article();
        articleNew.setAid(article.getId());
        articleNew.setTitle(article.getTitle());
        articleNew.setFeed_title(article.getFeed_title());
        articleNew.setTime(article.getTime());
        articleNew.setImg(article.getImg());
        articleNew.setTimestamp(System.currentTimeMillis());
        return articleNew;
    }

}
