package com.minerva.business.home.subscribe;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.site.menu.model.MenuModel;
import com.minerva.common.BlankViewModel;
import com.minerva.common.Constants;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtils;
import com.minerva.utils.ResourceUtils;
import com.minerva.widget.Loading;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class SubscribeViewModel extends BaseViewModel implements SiteGroupViewModel.ISubscribeNavClickListener, PopupMenu.OnMenuItemClickListener {
    public ObservableBoolean showNetworkError = new ObservableBoolean();
    public OnItemBind<BaseViewModel> siteGroupItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            if (item instanceof SiteGroupViewModel) {
                itemBinding.set(BR.subscribeGroupVM, R.layout.item_subscribe_site_group_layout);
            }
        }
    };
    public OnItemBind<BaseViewModel> siteChildItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            switch (item.getViewType()) {
                case Constants.RecyclerItemType.SUBSCRIBE_SITE_CHILD_ITEM_TYPE:
                    itemBinding.set(BR.subscribeChildVM, R.layout.item_subscribe_site_child_layout);
                    break;
                case Constants.RecyclerItemType.BLANK_TYPE:
                    itemBinding.set(BR.vmBlank, R.layout.item_blank_layout);
                    break;
            }
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
            if (item.getItemId() == R.id.siteMore) {
                showPopupMenu();
            }
            return true;
        }
    };
    private Loading loading;
    private BlankViewModel mBlankVM;
    private List<SubscribeBean.NaviBean> navBeanList = new ArrayList<>();
    private List<SubscribeBean.ItemsBean> itemBeanList = new ArrayList<>();
    private List<SubscribeBean.ItemsBean> enItemBeanList = new ArrayList<>();
    private boolean onlyEnglish, firstRequest = true;
    private String mNavId = "1";

    SubscribeViewModel(Context context) {
        super(context);
    }

    public ObservableList<BaseViewModel> getGroupItems() {
        return SubscribeModel.getInstance().getGroupData();
    }

    public ObservableList<BaseViewModel> getChildItems() {
        return SubscribeModel.getInstance().getChildData();
    }

    public void retry() {
        requestServer();
    }

    @Override
    public void onResume() {
        super.onResume();
        requestServer();
    }

    @Override
    public void onNavClick(String navId) {
        this.mNavId = navId;
        requestServer();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menu_site_all) {
            onlyEnglish = false;
            createViewModel();
            return true;
        }
        if (item.getItemId() == R.id.menu_site_english) {
            onlyEnglish = true;
            createViewModel();
            return true;
        }
        return false;
    }

    private void requestServer() {
        if (!CommonUtils.isNetworkAvailable(context)) {
            showNetworkError.set(firstRequest);
            setErrorPage();
            return;
        }

        firstRequest = false;
        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }

        getSubscribeSite(mNavId);
    }

    private void getSubscribeSite(final String cid) {
        MenuModel.getInstance().getSubscribeSite(cid, new NetworkObserver<SubscribeBean>() {
            @Override
            public void onSuccess(SubscribeBean subscribeBean) {
                loading.dismiss();
                showNetworkError.set(false);
                setLocalData(subscribeBean, cid);
                createViewModel();
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
            }
        });
    }

    private void setLocalData(SubscribeBean subscribeBean, String cid) {
        navBeanList.clear();
        for (SubscribeBean.NaviBean nav : subscribeBean.getNavi()) {
            if (nav != null) {
                nav.setSelected(TextUtils.equals(String.valueOf(nav.getId()), cid));
            }
            navBeanList.add(nav);
        }
        itemBeanList.clear();
        itemBeanList.addAll(subscribeBean.getItems());

        enItemBeanList.clear();
        for (SubscribeBean.ItemsBean item : itemBeanList) {
            if (item != null && item.getLang() == 2) {//过滤出英文站点
                enItemBeanList.add(item);
            }
        }
    }

    private void createViewModel() {
        SubscribeModel.getInstance().setGroupData(getGroupObserverData(navBeanList));
        if (onlyEnglish) {
            SubscribeModel.getInstance().setChildData(getChildObserverData(enItemBeanList));
        } else {
            SubscribeModel.getInstance().setChildData(getChildObserverData(itemBeanList));
        }
    }

    private ObservableList<BaseViewModel> getGroupObserverData(List<SubscribeBean.NaviBean> navi) {
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        if (navi.size() <= 0) {
            return temp;
        }

        for (SubscribeBean.NaviBean nav : navi) {
            if (nav == null) {
                continue;
            }
            SiteGroupViewModel groupViewModel = new SiteGroupViewModel(context, this);
            groupViewModel.groupName.set(nav.getName());
            groupViewModel.isSelected.set(nav.isSelected());
            groupViewModel.setId(String.valueOf(nav.getId()));
            temp.add(groupViewModel);
        }
        return temp;
    }

    private ObservableList<BaseViewModel> getChildObserverData(List<SubscribeBean.ItemsBean> items) {
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        if (items.size() <= 0) {
            if (mBlankVM == null) {
                mBlankVM = new BlankViewModel(context);
            }
            mBlankVM.setStatus(Constants.PageStatus.NO_DATA);
            temp.add(mBlankVM);
            return temp;
        }

        for (SubscribeBean.ItemsBean item : items) {
            if (item == null) {
                continue;
            }
            SiteChildViewModel childViewModel = new SiteChildViewModel(context);
            childViewModel.childName.set(item.getName());
            childViewModel.childIcon.set(item.getImage());
            childViewModel.isSelected.set(item.isFollowed());
            childViewModel.setId(item.getId());
            temp.add(childViewModel);
        }
        return temp;
    }

    private void setErrorPage() {
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        if (mBlankVM == null) {
            mBlankVM = new BlankViewModel(context);
        }
        SubscribeModel.getInstance().clearChild();
        mBlankVM.setStatus(Constants.PageStatus.NETWORK_EXCEPTION);
        temp.add(mBlankVM);
        SubscribeModel.getInstance().setChildData(temp);
    }

    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(context, ((BaseActivity) context).getWindow().getDecorView().getRootView().findViewById(R.id.site_toolbar));
        popupMenu.getMenuInflater().inflate(R.menu.subscribe_popup_menu, popupMenu.getMenu());
        popupMenu.setGravity(Gravity.END);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }

}
