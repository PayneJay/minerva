package com.minerva.business.site.menu;

import android.content.Context;
import android.widget.Toast;

import com.minerva.base.BaseBean;
import com.minerva.business.site.menu.model.MenuModel;
import com.minerva.business.site.imperfect.SiteFragment;
import com.minerva.network.NetworkObserver;
import com.minerva.widget.Loading;

import org.greenrobot.eventbus.EventBus;

public class ChildMenuViewModel extends MenuViewModel {
    public ChildMenuViewModel(Context context, IPopupMenuItemClickListener listener) {
        super(context, listener);
    }

    public void onMoveClick() {
        if (listener != null) {
            listener.onMenuOperateSuccess();
        }

        showPopupMenu(SiteFragment.TYPE_CHILD);
    }

    public void onUnsubscribeClick() {
        if (listener != null) {
            listener.onMenuOperateSuccess();
        }

        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }

        MenuModel.getInstance().markUnFollow(getChildId(), new NetworkObserver<BaseBean>() {
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
        });
    }
}
