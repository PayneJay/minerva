package com.minerva.business.mine.collection.model;

import com.minerva.business.article.list.model.ArticleListBean;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CollectionModel {
    private static CollectionModel instance;

    public static CollectionModel getInstance() {
        if (instance == null) {
            instance = new CollectionModel();
        }
        return instance;
    }

    /**
     * 获取我的收藏列表，默认加载一页30条
     *
     * @param observer 回调
     */
    public void getMyCollections(int page, Observer<? super ArticleListBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getMyCollections(page, 30, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 添加收藏
     *
     * @param id       文章id
     * @param cat      类型
     * @param observer 回调
     */
    public void addCollections(String id, String cat, Observer<? super UnFavBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .addFavorite(id, cat)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 取消收藏
     *
     * @param id       文章ID
     * @param observer 回调
     */
    public void cancelCollections(String id, Observer<? super UnFavBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .removeFavorite(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
