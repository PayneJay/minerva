package com.minerva.business.mine.journal;

import android.content.Context;

import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;

public class JournalItemViewModel extends BaseViewModel {
    public String journalName;
    public String articleCount;
    private String id;

    public JournalItemViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.MY_JOURNAL_ITEM_TYPE);
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void onItemClick() {

    }
}
