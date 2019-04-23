package com.minerva.business.settings.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.business.settings.RecItemViewModel;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList;

public class SettingModel {
    private static SettingModel instance;
    private DiffObservableList<BaseViewModel> mData;

    public static SettingModel getInstance() {
        if (instance == null) {
            instance = new SettingModel();
        }

        return instance;
    }

    /**
     * 获取阅读设置
     *
     * @param observer 回调
     */
    public void getReadSetting(Observer<? super ReadSettingBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getReadSetting()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 更新阅读推荐
     *
     * @param tech     科技类
     * @param design   设计类
     * @param guru     技术类
     * @param observer 回调
     */
    public void updateRead(String tech, String design, String guru, Observer<? super BaseBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .updateRead(tech, design, guru)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public DiffObservableList<BaseViewModel> getData() {
        if (mData == null) {
            mData = new DiffObservableList<>(new DiffObservableList.Callback<BaseViewModel>() {
                @Override
                public boolean areItemsTheSame(BaseViewModel oldItem, BaseViewModel newItem) {
                    if (oldItem instanceof RecItemViewModel && newItem instanceof RecItemViewModel) {
                        return ((RecItemViewModel) oldItem).getId().equalsIgnoreCase(((RecItemViewModel) newItem).getId());
                    }
                    return oldItem.equals(newItem);
                }

                @Override
                public boolean areContentsTheSame(BaseViewModel oldItem, BaseViewModel newItem) {
                    return oldItem.equals(newItem);
                }
            });
        }
        return mData;
    }

    public void clear() {
        getData().update(new ObservableArrayList<BaseViewModel>());
    }

    public void setData(ObservableList<BaseViewModel> observableList) {
        getData().update(observableList);
    }

    public void onDestroy() {
        clear();
        instance = null;
    }
}
