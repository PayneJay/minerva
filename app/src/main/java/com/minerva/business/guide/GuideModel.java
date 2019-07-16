package com.minerva.business.guide;

import com.minerva.base.BaseBean;
import com.minerva.business.site.model.SitesBean;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GuideModel {
    private static GuideModel instance;

    public static GuideModel getInstance() {
        if (instance == null) {
            instance = new GuideModel();
        }
        return instance;
    }

    /**
     * 获取订阅站点列表
     *
     * @param observer 回调
     */
    public void getColdSiteList(Observer<? super SitesBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getColdSiteList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 订阅站点
     *
     * @param items    站点id
     * @param observer 回调
     */
    public void followSite(String items, Observer<BaseBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .followSite(items)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
