package com.minerva.business.home.menu;

import com.minerva.base.BaseBean;
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
    public void sortGroups(String order, Observer<? super BaseBean> observer) {
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
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .markAllRead()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 标记聚合全部已读
     *
     * @param did      id
     * @param observer 回调
     */
    public void markJuheRead(int did, Observer<? super BaseBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .markJuheRead(did)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 取消訂閱
     *
     * @param id       id
     * @param observer 回调
     */
    public void markUnFollow(String id, Observer<? super BaseBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .markUnFollow(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * @param id       id
     * @param name     新名字
     * @param observer 回调
     */
    public void renameGroup(int id, String name, Observer<? super BaseBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .renameGroup(id, name, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 移动分组
     *
     * @param tid      目标组
     * @param fid      要迁移的组
     * @param observer 回调
     */
    public void transferItems(int tid, int fid, Observer<? super BaseBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .transferItems(tid, fid, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 移动到分组
     *
     * @param did      目标组
     * @param sid      要迁移条目
     * @param observer 回调
     */
    public void moveSource(int did, String sid, Observer<? super BaseBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .moveSource(did, sid, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 删除分组
     *
     * @param did      目标组
     * @param observer 回调
     */
    public void removeGroup(int did, Observer<? super BaseBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .removeGroup(did, 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
