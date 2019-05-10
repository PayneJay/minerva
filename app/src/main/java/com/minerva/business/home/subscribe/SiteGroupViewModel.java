package com.minerva.business.home.subscribe;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.minerva.base.BaseViewModel;

public class SiteGroupViewModel extends BaseViewModel {
    public ObservableField<String> groupName = new ObservableField<>();
    public ObservableBoolean isSelected = new ObservableBoolean();
    private ISubscribeNavClickListener listener;
    private String id;

    SiteGroupViewModel(Context context, ISubscribeNavClickListener listener) {
        super(context);
        this.listener = listener;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void onItemClick() {
        if (listener != null) {
            listener.onNavClick(id);
        }
    }

    public interface ISubscribeNavClickListener {
        void onNavClick(String navId);
    }
}
