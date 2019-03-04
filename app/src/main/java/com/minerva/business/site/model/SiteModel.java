package com.minerva.business.site.model;


import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SiteModel {
    private static SiteModel instance;

    private SiteModel() {
    }

    public static SiteModel getInstance() {
        if (instance == null) {
            instance = new SiteModel();
        }
        return instance;
    }

    public void getSiteList(Observer<? super SitesBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getSiteList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
