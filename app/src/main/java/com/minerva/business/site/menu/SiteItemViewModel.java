package com.minerva.business.site.menu;

import android.content.Context;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.widget.Toast;

import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.business.home.menu.MenuModel;
import com.minerva.network.NetworkObserver;
import com.minerva.widget.Loading;

import org.greenrobot.eventbus.EventBus;

public class SiteItemViewModel extends BaseViewModel {
    public ObservableField<String> name = new ObservableField<>();
    private IPopupMenuItemClickListener listener;
    private Loading loading;
    private String sourceId;
    private int targetId;
    private int fromId;

    SiteItemViewModel(Context context, IPopupMenuItemClickListener listener) {
        super(context);
        this.listener = listener;
    }

    void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public void onItemClick() {
        if (listener != null) {
            listener.onMenuItemClick();
        }

        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }

        NetworkObserver<BaseBean> observer = new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                loading.dismiss();
                EventBus.getDefault().postSticky(baseBean);
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        };
        if (TextUtils.isEmpty(sourceId)) {
            MenuModel.getInstance().transferItems(targetId, fromId, observer);
        } else {
            MenuModel.getInstance().moveSource(targetId, sourceId, observer);
        }
    }
}
