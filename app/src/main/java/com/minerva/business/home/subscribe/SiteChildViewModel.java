package com.minerva.business.home.subscribe;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.business.site.detail.PeriodicalDetailActivity;
import com.minerva.business.site.menu.model.MenuModel;
import com.minerva.common.Constants;
import com.minerva.common.GlobalData;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtil;
import com.minerva.utils.ResourceUtil;
import com.minerva.utils.ToastUtil;
import com.minerva.widget.Loading;

public class SiteChildViewModel extends BaseViewModel {
    public ObservableField<String> childIcon = new ObservableField<>();
    public ObservableField<String> childName = new ObservableField<>();
    public ObservableBoolean isSelected = new ObservableBoolean();
    private String id;
    private Loading loading;

    public SiteChildViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.SUBSCRIBE_SITE_CHILD_ITEM_TYPE);
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * 点击添加/取消订阅按钮
     */
    public void onSelectedChanged() {
        if (!GlobalData.getInstance().isLogin()) {
            ToastUtil.showMsg(ResourceUtil.getString(R.string.toast_please_login_first));
            return;
        }
        if (!CommonUtil.isNetworkAvailable(context)) {
            ToastUtil.showMsg(ResourceUtil.getString(R.string.network_error));
            return;
        }

        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }

        if (isSelected.get()) {//当前是已订阅，则取消订阅
            markUnFollow();
        } else {//否则订阅
            markFollow();
        }
    }

    public void onItemClick() {
        Intent intent = new Intent(context, PeriodicalDetailActivity.class);
        intent.putExtra(Constants.KeyExtra.PERIODICAL_ID, id);
        intent.putExtra(Constants.KeyExtra.PERIODICAL_IMAGE, childIcon.get());
        intent.putExtra(Constants.KeyExtra.PERIODICAL_NAME, childName.get());
        context.startActivity(intent);
    }

    /**
     * 标记订阅
     */
    private void markFollow() {
        MenuModel.getInstance().markFollow(id, new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                loading.dismiss();
                isSelected.set(true);
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });
    }

    /**
     * 取消订阅
     */
    private void markUnFollow() {
        MenuModel.getInstance().markUnFollow(id, new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                loading.dismiss();
                isSelected.set(false);
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });
    }
}
