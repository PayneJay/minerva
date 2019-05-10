package com.minerva.business.category.book.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.minerva.base.BaseViewModel;
import com.minerva.business.category.book.BookChildViewModel;
import com.minerva.business.category.mag.SpecialGroupViewModel;
import com.minerva.business.mine.journal.JournalItemViewModel;

import me.tatarka.bindingcollectionadapter2.collections.DiffObservableList;

public class BookModel {
    private static BookModel instance;
    private DiffObservableList<BaseViewModel> mData;

    public static BookModel getInstance() {
        if (instance == null) {
            instance = new BookModel();
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
                    if (oldItem instanceof BookChildViewModel && newItem instanceof BookChildViewModel) {
                        return ((BookChildViewModel) oldItem).getId().equalsIgnoreCase(((BookChildViewModel) newItem).getId());
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
