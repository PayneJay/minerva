package com.minerva.business.category.column.model;

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

    public void getMagDetailDetail(String magID, int type, Observer<? super MagDetailBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getMagDetailDetail(magID, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
