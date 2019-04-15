package com.minerva.business.mine.journal.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.collection.model.KanBean;
import com.minerva.business.mine.journal.JournalItemViewModel;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList;

public class JournalModel {
    private static JournalModel instance;
    private DiffObservableList<BaseViewModel> mData;
    private List<KanBean.ItemsBean> kanList = new ArrayList<>();

    public static JournalModel getInstance() {
        if (instance == null) {
            instance = new JournalModel();
        }
        return instance;
    }

    public void setKanList(List<KanBean.ItemsBean> kanList) {
        this.kanList.clear();
        this.kanList.addAll(kanList);
    }

    public List<KanBean.ItemsBean> getKanList() {
        if (kanList == null) {
            return new ArrayList<>();
        }
        return kanList;
    }

    public void createJournal(String name, String desc, int type, Observer<? super KanBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .createJournal(name, desc, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取收藏夹列表
     *
     * @param observer 回调
     */
    public void getKans(Observer<? super KanBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getKanList()
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

}
