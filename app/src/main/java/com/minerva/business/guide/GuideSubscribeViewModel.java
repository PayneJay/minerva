package com.minerva.business.guide;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.business.site.model.SitesBean;
import com.minerva.common.Constants;
import com.minerva.network.NetworkObserver;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class GuideSubscribeViewModel extends BaseViewModel {
    public String mTitle;
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
                followSite();
            }
            return true;
        }
    };

    public GuideSubscribeViewModel(Context context) {
        super(context);
        getCloudSiteList();
    }

    public GridLayoutManager getGlManager() {
        return new GridLayoutManager(context, 2);
    }

    private void getCloudSiteList() {
        GuideModel.getInstance().getColdSiteList(new NetworkObserver<SitesBean>() {
            @Override
            public void onSuccess(SitesBean sitesBean) {
                createViewModel(sitesBean.getItems());
            }

            @Override
            public void onFailure(String msg) {

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
                Log.e(Constants.TAG, "followSite : " + baseBean.isSuccess());
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
