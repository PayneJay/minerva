package com.minerva.business.category.mag.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.minerva.base.BaseViewModel;
import com.minerva.business.category.book.BookChildViewModel;
import com.minerva.business.category.mag.SpecialChildViewModel;
import com.minerva.business.category.mag.SpecialGroupViewModel;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList;

public class MagModel {
    private static MagModel instance;
    private DiffObservableList<BaseViewModel> mData;

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

    public DiffObservableList<BaseViewModel> getData() {
        if (mData == null) {
            mData = new DiffObservableList<>(new DiffObservableList.Callback<BaseViewModel>() {
                @Override
                public boolean areItemsTheSame(BaseViewModel oldItem, BaseViewModel newItem) {
                    if (oldItem instanceof SpecialGroupViewModel && newItem instanceof SpecialGroupViewModel) {
                        return ((SpecialGroupViewModel) oldItem).getId().equalsIgnoreCase(((SpecialGroupViewModel) newItem).getId());
                    }
                    if (oldItem instanceof SpecialChildViewModel && newItem instanceof SpecialChildViewModel) {
                        return ((SpecialChildViewModel) oldItem).magID.equalsIgnoreCase(((SpecialChildViewModel) newItem).magID);
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
