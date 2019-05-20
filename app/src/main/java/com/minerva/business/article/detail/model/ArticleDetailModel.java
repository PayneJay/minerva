package com.minerva.business.article.detail.model;


import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minerva.base.BaseBean;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;
import com.minerva.utils.CommonUtils;
import com.minerva.utils.SPUtils;

import java.util.LinkedHashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ArticleDetailModel {
    private static ArticleDetailModel instance;
    private LinkedHashMap<String, Object> readArticleMap = new LinkedHashMap<>();

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
     * 获取已添加的待读文章（未登录）
     *
     * @param context context
     * @param key     sp缓存的key
     * @return map
     */
    public LinkedHashMap<String, Object> getArticlesByKey(Context context, String key) {
        String history = (String) SPUtils.get(context, key, "");
        if (TextUtils.isEmpty(history)) {
            return readArticleMap;
        }

        LinkedHashMap<String, ArticleDetailBean.ArticleBean> map = new Gson().fromJson(history, new TypeToken<LinkedHashMap<String, ArticleDetailBean.ArticleBean>>() {
        }.getType());

        readArticleMap.clear();
        readArticleMap.putAll(map);
        return readArticleMap;
    }

    /**
     * 添加待读文章（未登录）
     *
     * @param context context
     * @param article 待读文章
     * @param key     sp缓存的key
     */
    public void addArticleWithKey(Context context, ArticleDetailBean.ArticleBean article, String key) {
        LinkedHashMap<String, Object> readLater = getArticlesByKey(context, key);
        readLater.put(article.getId(), article);

        String mapString = CommonUtils.toJson(readLater);
        SPUtils.put(context, key, mapString);
    }

    /**
     * 取消待读文章（未登录）
     *
     * @param context context
     * @param id      待读文章
     * @param key     sp缓存的key
     */
    public void removeArticleByKey(Context context, String id, String key) {
        LinkedHashMap<String, Object> readLater = getArticlesByKey(context, key);
        readLater.remove(id);

        String map = CommonUtils.toJson(readLater);
        SPUtils.put(context, key, map);
    }

}
