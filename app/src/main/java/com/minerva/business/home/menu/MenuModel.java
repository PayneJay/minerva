package com.minerva.business.home.menu;

import com.minerva.base.BaseBean;
import com.minerva.business.home.weekly.model.WeekListBean;
import com.minerva.business.site.model.SitesBean;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MenuModel {
    private static MenuModel instance;

    public static MenuModel getInstance() {
        if (instance == null) {
            instance = new MenuModel();
        }
        return instance;
    }

    /**
     * 创建新的分组
     *
     * @param name     组名
     * @param observer 回调
     */
    public void createGroup(String name, Observer<? super SitesBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .createGroup(name, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 分组排序
     *
     * @param order    顺序，中间用"，"分割
     * @param observer 回调
     */
    public void sortGroups(String order, Observer<? super SitesBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .sortGroups(order, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 标记全部已读
     *
     * @param observer 回调
     */
    public void markAllRead(Observer<? super BaseBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .MarkAllRead()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
