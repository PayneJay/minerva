package com.minerva.business.mine.message.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.message.MessageItemViewModel;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList;

public class MsgModel {
    private static MsgModel instance;
    private DiffObservableList<BaseViewModel> mData;

    public static MsgModel getInstance() {
        if (instance == null) {
            instance = new MsgModel();
        }
        return instance;
    }

    /**
     * 获取消息通知列表
     *
     * @param observer 回调
     */
    public void getMessageList(Observer<? super MsgListBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getMessageList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public DiffObservableList<BaseViewModel> getData() {
        if (mData == null) {
            mData = new DiffObservableList<>(new DiffObservableList.Callback<BaseViewModel>() {
                @Override
                public boolean areItemsTheSame(BaseViewModel oldItem, BaseViewModel newItem) {
                    if (oldItem instanceof MessageItemViewModel && newItem instanceof MessageItemViewModel) {
                        return ((MessageItemViewModel) oldItem).getId().equalsIgnoreCase(((MessageItemViewModel) newItem).getId());
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
