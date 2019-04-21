package com.minerva.business.mine.journal;

import android.content.Context;
import android.databinding.ObservableBoolean;

import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.collection.model.KanBean;
import com.minerva.common.Constants;

public class JournalItemViewModel extends BaseViewModel {
    public ObservableBoolean canEdit = new ObservableBoolean(false);
    public ObservableBoolean isSelected = new ObservableBoolean(false);
    public String journalName;
    public String articleCount;
    private String id;
    private IItemClickListener mListener;

    JournalItemViewModel(Context context, KanBean.ItemsBean item) {
        super(context);
        setViewType(Constants.RecyclerItemType.MY_JOURNAL_ITEM_TYPE);
        journalName = item.getName();
        articleCount = item.getAc() + "";
        id = item.getId();
    }

    void setIsSelected(boolean isSelected) {
        if (canEdit.get()) {
            this.isSelected.set(isSelected);
        }
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setListener(IItemClickListener listener) {
        this.mListener = listener;
    }

    public void onItemClick() {
        if (canEdit.get() && mListener != null) {
            mListener.onItemClick(id);
        }
    }

    public interface IItemClickListener {
        void onItemClick(String id);
    }
}
