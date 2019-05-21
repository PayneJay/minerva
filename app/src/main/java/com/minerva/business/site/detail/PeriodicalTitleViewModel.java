package com.minerva.business.site.detail;

import android.content.Context;
import android.databinding.ObservableField;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.business.site.menu.model.MenuModel;
import com.minerva.common.Constants;
import com.minerva.common.GlobalData;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtil;
import com.minerva.utils.ResourceUtil;
import com.minerva.widget.Loading;

import org.greenrobot.eventbus.EventBus;

public class PeriodicalTitleViewModel extends BaseViewModel {
    public ObservableField<String> imgUrl = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> subscribe = new ObservableField<>(ResourceUtil.getString(R.string.periodical_subscribe));
    private Loading loading;
    private String id;
    private boolean isFollow;

    PeriodicalTitleViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.PERIODICAL_TITLE_TYPE);
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFollow() {
        return isFollow;
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
    }

    public void onSubscribeClick() {
        if (!GlobalData.getInstance().isLogin()) {
            Toast.makeText(context, ResourceUtil.getString(R.string.toast_please_login_first), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!CommonUtil.isNetworkAvailable(context)) {
            Toast.makeText(context, ResourceUtil.getString(R.string.network_error), Toast.LENGTH_SHORT).show();
            return;
        }

        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }

        if (isFollow) {
            markUnFollow();
        } else {
            markFollow();
        }
    }

    private void markFollow() {
        MenuModel.getInstance().markFollow(id, new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                loading.dismiss();
                subscribe.set(ResourceUtil.getString(R.string.periodical_unsubscribe));
                EventBus.getDefault().postSticky(baseBean);
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });
    }

    private void markUnFollow() {
        MenuModel.getInstance().markUnFollow(id, new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                loading.dismiss();
                subscribe.set(ResourceUtil.getString(R.string.periodical_subscribe));
                EventBus.getDefault().postSticky(baseBean);
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });
    }
}
