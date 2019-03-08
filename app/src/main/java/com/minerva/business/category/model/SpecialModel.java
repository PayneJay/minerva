package com.minerva.business.category.model;

import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;
import com.minerva.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SpecialModel {
    private static SpecialModel instance;

    private SpecialModel() {
    }

    public static SpecialModel getInstance() {
        if (instance == null) {
            instance = new SpecialModel();
        }
        return instance;
    }

    public List<SpecialBean.ItemsBeanX> generateColumnData() {
        SpecialBean bean = CommonUtils.getSpecialListFromJson();
        List<SpecialBean.ItemsBeanX> list = new ArrayList<>();
        list.addAll(bean.getItems());
        return list;
    }

    public List<BookBean.ItemsBean> generateBookData() {
        BookBean bean = CommonUtils.getBookListFromJson();
        List<BookBean.ItemsBean> list = new ArrayList<>();
        list.addAll(bean.getItems());
        return list;
    }

    public void getSpecialList(Observer<? super SpecialBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getSpecialList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void getBookList(Observer<? super BookBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getBookList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
