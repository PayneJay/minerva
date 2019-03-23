package com.minerva.business.mine.collection.model;

import com.minerva.base.BaseBean;
import com.minerva.business.article.list.model.ArticleBean;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CollectionModel {
    private static CollectionModel instace;

    public static CollectionModel getInstace() {
        if (instace == null) {
            instace = new CollectionModel();
        }
        return instace;
    }

    /**
     * 获取我的收藏列表，默认加载一页30条
     *
     * @param observer 回调
     */
    public void getMyCollections(int page, Observer<? super ArticleBean> observer) {
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

    /**
     * 获取收藏夹列表
     *
     * @param observer 回调
     */
    public void getKans(Observer<? super KanBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getKanList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
