package com.minerva.business.search;

import android.content.Context;
import android.databinding.ObservableField;

import com.minerva.base.BaseViewModel;
import com.minerva.business.search.model.SearchModel;
import com.minerva.common.Constants;
import com.minerva.common.EventMsg;
import com.minerva.db.SearchHistory;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class SearchHistoryViewModel extends BaseViewModel {
    public ObservableField<String> keyword = new ObservableField<>();

    SearchHistoryViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.SEARCH_KW_HISTORY_TYPE);
    }

    public void onItemClick() {
        EventBus.getDefault().post(new EventMsg(Constants.EventMsgKey.QUERY_ECHO, keyword.get()));
    }

    public void onDeleteKW() {
        SearchModel.getInstance().deleteHistoryByKey(keyword.get().trim());
        EventBus.getDefault().post(new EventMsg(Constants.EventMsgKey.DELETE_SEARCH_KEYWORD));
    }
}
