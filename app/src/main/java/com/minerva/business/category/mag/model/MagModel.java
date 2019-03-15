package com.minerva.business.category.mag.model;

import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MagModel {
    private static MagModel instance;

    public static MagModel getInstance() {
        if (instance == null) {
            instance = new MagModel();
        }
        return instance;
    }

    /**
     * 获取期刊详情
     *
     * @param magID    期刊id
     * @param type     期刊类型
     * @param observer 回调
     */
    public void getMagDetail(String magID, int type, Observer<? super MagDetailBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getMagDetail(magID, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取全部期刊列表
     *
     * @param type     期刊类型
     * @param observer 回调
     */
    public void getMagPeriodList(int type, Observer<? super MagPeriod> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getMagPeriodList(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
