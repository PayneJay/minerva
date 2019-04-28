package com.minerva.business.home.subscribe;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.journal.JournalItemViewModel;
import com.minerva.common.BlankViewModel;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList;

public class SubscribeModel {
    private static SubscribeModel instance;
    private DiffObservableList<BaseViewModel> mChildData;
    private DiffObservableList<BaseViewModel> mGroupData;

    public static SubscribeModel getInstance() {
        if (instance == null) {
            instance = new SubscribeModel();
        }
        return instance;
    }

    DiffObservableList<BaseViewModel> getChildData() {
        if (mChildData == null) {
            mChildData = new DiffObservableList<>(new DiffObservableList.Callback<BaseViewModel>() {
                @Override
                public boolean areItemsTheSame(BaseViewModel oldItem, BaseViewModel newItem) {
                    if (oldItem instanceof SiteChildViewModel && newItem instanceof SiteChildViewModel) {
                        return ((SiteChildViewModel) oldItem).getId().equalsIgnoreCase(((SiteChildViewModel) newItem).getId());
                    }
                    if (oldItem instanceof BlankViewModel && newItem instanceof BlankViewModel) {
                        return ((BlankViewModel) oldItem).getId().equalsIgnoreCase(((BlankViewModel) newItem).getId());
                    }
                    return oldItem.equals(newItem);
                }

                @Override
                public boolean areContentsTheSame(BaseViewModel oldItem, BaseViewModel newItem) {
                    return oldItem.equals(newItem);
                }
            });
        }
        return mChildData;
    }

    void clearChild() {
        getChildData().update(new ObservableArrayList<BaseViewModel>());
    }

    void setChildData(ObservableList<BaseViewModel> observableList) {
        getChildData().update(observableList);
    }

    DiffObservableList<BaseViewModel> getGroupData() {
        if (mGroupData == null) {
            mGroupData = new DiffObservableList<>(new DiffObservableList.Callback<BaseViewModel>() {
                @Override
                public boolean areItemsTheSame(BaseViewModel oldItem, BaseViewModel newItem) {
                    if (oldItem instanceof SiteGroupViewModel && newItem instanceof SiteGroupViewModel) {
                        return ((SiteGroupViewModel) oldItem).getId().equalsIgnoreCase(((SiteGroupViewModel) newItem).getId());
                    }
                    return oldItem.equals(newItem);
                }

                @Override
                public boolean areContentsTheSame(BaseViewModel oldItem, BaseViewModel newItem) {
                    return oldItem.equals(newItem);
                }
            });
        }
        return mGroupData;
    }

    void clearGroup() {
        getChildData().update(new ObservableArrayList<BaseViewModel>());
    }

    void setGroupData(ObservableList<BaseViewModel> observableList) {
        getGroupData().update(observableList);
    }

    public void onDestroy() {
        clearGroup();
        clearChild();
        instance = null;
    }
}
