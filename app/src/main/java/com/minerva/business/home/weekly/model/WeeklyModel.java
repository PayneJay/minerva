package com.minerva.business.home.weekly.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.journal.JournalItemViewModel;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList;

public class WeeklyModel {
    private static WeeklyModel instance;
    private DiffObservableList<BaseViewModel> mData;

    public static WeeklyModel getInstance() {
        if (instance == null) {
            instance = new WeeklyModel();
        }
        return instance;
    }

    /**
     * 获取一周拾遗列表
     *
     * @param observer 回调
     */
    public void getWeeklyList(Observer<? super WeekListBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getWeeklyList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取一周拾遗详情列表
     *
     * @param id       id
     * @param observer 回调
     */
    public void getWeeklyDetail(String id, Observer<? super WeekDetailBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getWeeklyDetail(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public DiffObservableList<BaseViewModel> getData() {
        if (mData == null) {
            mData = new DiffObservableList<>(new DiffObservableList.Callback<BaseViewModel>() {
                @Override
                public boolean areItemsTheSame(BaseViewModel oldItem, BaseViewModel newItem) {
                    if (oldItem instanceof JournalItemViewModel && newItem instanceof JournalItemViewModel) {
                        return ((JournalItemViewModel) oldItem).getId().equalsIgnoreCase(((JournalItemViewModel) newItem).getId());
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
