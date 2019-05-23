package com.minerva.business.mine.read.model;

import com.minerva.business.article.list.model.ArticleListBean;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReadModel {
    private static ReadModel instance;

    public static ReadModel getInstance() {
        if (instance == null) {
            instance = new ReadModel();
        }
        return instance;
    }

    /**
     * 获取待读列表
     *
     * @param observer 回调
     */
    public void getLateList(Observer<? super ArticleListBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getLateList(200, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
