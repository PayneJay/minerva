package com.minerva.business.category.mag.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.minerva.base.BaseViewModel;
import com.minerva.business.category.mag.MagChildViewModel;
import com.minerva.business.category.mag.SpecialChildViewModel;
import com.minerva.business.category.mag.SpecialGroupViewModel;

import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList;

public class MagDetailModel {
    private static MagDetailModel instance;
    private DiffObservableList<BaseViewModel> mData;

    public static MagDetailModel getInstance() {
        if (instance == null) {
            instance = new MagDetailModel();
        }
        return instance;
    }

    public DiffObservableList<BaseViewModel> getData() {
        if (mData == null) {
            mData = new DiffObservableList<>(new DiffObservableList.Callback<BaseViewModel>() {
                @Override
                public boolean areItemsTheSame(BaseViewModel oldItem, BaseViewModel newItem) {
                    if (oldItem instanceof SpecialGroupViewModel && newItem instanceof SpecialGroupViewModel) {
                        return ((SpecialGroupViewModel) oldItem).getId().equalsIgnoreCase(((SpecialGroupViewModel) newItem).getId());
                    }
                    if (oldItem instanceof MagChildViewModel && newItem instanceof MagChildViewModel) {
                        return ((MagChildViewModel) oldItem).articleID.equalsIgnoreCase(((MagChildViewModel) newItem).articleID);
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
