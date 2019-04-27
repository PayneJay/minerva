package com.minerva.business.site.menu;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.business.site.model.SiteModel;
import com.minerva.business.site.model.SitesBean;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class SiteViewModel extends BaseViewModel {
    public ObservableList<BaseViewModel> items = new ObservableArrayList<>();
    public OnItemBind<BaseViewModel> siteItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            if (item instanceof SiteItemViewModel) {
                itemBinding.set(BR.siteItemVM, R.layout.item_site_name_layout);
            }
        }
    };
    private String childId;
    private int groupId;

    SiteViewModel(Context context) {
        super(context);
    }

    void setChildId(String childId) {
        this.childId = childId;
    }

    void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    void createItemViewModel(IPopupMenuItemClickListener listener) {
        List<SitesBean.ItemsBeanX> itemList = SiteModel.getInstance().getItemList();
        for (SitesBean.ItemsBeanX item : itemList) {
            if (item.getId() == groupId) {
                continue;
            }
            SiteItemViewModel itemViewModel = new SiteItemViewModel(context, listener);
            itemViewModel.name.set(item.getName());
            itemViewModel.setTargetId(item.getId());
            itemViewModel.setFromId(groupId);
            itemViewModel.setSourceId(childId);
            items.add(itemViewModel);
        }
    }
}
