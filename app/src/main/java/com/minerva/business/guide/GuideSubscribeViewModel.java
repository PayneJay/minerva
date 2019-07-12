package com.minerva.business.guide;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.business.SplashActivity;
import com.minerva.business.home.HomeActivity;
import com.minerva.business.site.model.SitesBean;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtil;
import com.minerva.utils.ResourceUtil;
import com.minerva.widget.Loading;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class GuideSubscribeViewModel extends BaseViewModel {
    public String mTitle;
    public ObservableField<String> description = new ObservableField<>(ResourceUtil.getString(R.string.no_data));
    public ObservableInt drawableRes = new ObservableInt(R.drawable.icon_no_data);
    public ObservableBoolean isException = new ObservableBoolean(true);
    public ObservableList<BaseViewModel> items = new ObservableArrayList<>();
    public OnItemBind<BaseViewModel> guideItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            itemBinding.set(BR.guideItemVM, R.layout.item_guide_subscribe_layout);
        }
    };
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };
    public Toolbar.OnMenuItemClickListener menuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.next_step) {
                if (!isException.get()) {
                    followSite();
                }
            }
            return true;
        }
    };
    private Loading loading;

    public GuideSubscribeViewModel(Context context) {
        super(context);
        getCloudSiteList();
    }

    public GridLayoutManager getGlManager() {
        return new GridLayoutManager(context, 2);
    }

    private void getCloudSiteList() {
        if (!CommonUtil.isNetworkAvailable(context)) {
            isException.set(true);
            description.set(ResourceUtil.getString(R.string.network_error));
            drawableRes.set(R.drawable.icon_network_exception);
            return;
        }
        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }

        GuideModel.getInstance().getColdSiteList(new NetworkObserver<SitesBean>() {
            @Override
            public void onSuccess(SitesBean sitesBean) {
                loading.dismiss();
                isException.set(false);
                createViewModel(sitesBean.getItems());
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });
    }

    private void followSite() {
        StringBuilder builder = new StringBuilder();
        for (BaseViewModel viewModel : items) {
            if (viewModel instanceof GuideItemViewModel && ((GuideItemViewModel) viewModel).isSelected.get()) {
                builder.append(((GuideItemViewModel) viewModel).itemId).append(",");
            }
        }
        GuideModel.getInstance().followSite(builder.toString(), new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
                ((BaseActivity) context).finish();
            }

            @Override
            public void onFailure(String msg) {
            }
        });
    }

    private void createViewModel(List<SitesBean.ItemsBeanX> siteItems) {
        for (SitesBean.ItemsBeanX itemBeanX : siteItems) {
            if (itemBeanX != null) {
                List<SitesBean.ItemsBeanX.ItemsBean> items = itemBeanX.getItems();
                for (SitesBean.ItemsBeanX.ItemsBean item : items) {
                    if (item != null) {
                        GuideItemViewModel itemViewModel = new GuideItemViewModel(context);
                        itemViewModel.name.set(item.getName());
                        itemViewModel.itemId = item.getId();
                        this.items.add(itemViewModel);
                    }
                }
            }
        }
    }
}
