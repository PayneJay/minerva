package com.minerva.business.category.model;

import com.minerva.business.category.book.model.AllBook;
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

    public List<MagBean.ItemsBeanX> generateColumnData() {
        MagBean bean = CommonUtils.getSpecialListFromJson();
        List<MagBean.ItemsBeanX> list = new ArrayList<>();
        list.addAll(bean.getItems());
        return list;
    }

    public List<BookBean.ItemsBean> generateBookData() {
        BookBean bean = CommonUtils.getBookListFromJson();
        List<BookBean.ItemsBean> list = new ArrayList<>();
        list.addAll(bean.getItems());
        return list;
    }

    public void getSpecialList(Observer<? super MagBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getSpecialList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取图书列表
     *
     * @param observer 回调
     */
    public void getBookList(Observer<? super BookBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getBookList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取所有书籍列表
     *
     * @param tag      tag
     * @param pn       页码
     * @param observer 回调
     */
    public void getAllBookList(int tag, int pn, Observer<? super AllBook> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getAllBookList(tag, pn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
