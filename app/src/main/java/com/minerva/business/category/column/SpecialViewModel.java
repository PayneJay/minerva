package com.minerva.business.category.column;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.business.category.model.SpecialBean;
import com.minerva.business.category.model.SpecialModel;
import com.minerva.common.Constants;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class SpecialViewModel extends BaseViewModel {
    public ObservableBoolean refreshing = new ObservableBoolean();
    public ObservableList<BaseViewModel> items = new ObservableArrayList<>();
    public OnItemBind<BaseViewModel> specialItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            switch (item.getViewType()) {
                case Constants.RecyclerItemType.SPECIAL_GROUP_TYPE:
                    itemBinding.set(BR.specialGroupVM, R.layout.item_special_group_layout);
                    break;
                case Constants.RecyclerItemType.SPECIAL_CHILD_TYPE:
                    itemBinding.set(BR.specialChildVM, R.layout.item_special_child_layout);
                    break;
                case Constants.RecyclerItemType.BLANK_TYPE:
                    itemBinding.set(BR.vmBlank, R.layout.item_blank_layout);
                    break;
            }
        }
    };
    private List<SpecialBean.ItemsBeanX> beanXList = new ArrayList<>();

    SpecialViewModel(Context context) {
        super(context);
        requestServer();
    }

    public int[] getColors() {
        return new int[]{R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark};
    }

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            requestServer();
        }
    };

    private void requestServer() {
        refreshing.set(true);
        SpecialModel.getInstance().getSpecialList(new NetworkObserver<SpecialBean>() {
            @Override
            public void onSuccess(SpecialBean specialBean) {
                refreshing.set(false);
                beanXList.clear();
                beanXList.addAll(specialBean.getItems());
                createViewModel();
            }

            @Override
            public void onFailure() {
                refreshing.set(false);
                beanXList.clear();
                beanXList.addAll(SpecialModel.getInstance().generateColumnData());
                createViewModel();
            }
        });
    }

    private void createViewModel() {
        if (beanXList.size() <= 0) {
            return;
        }

        items.clear();
        for (SpecialBean.ItemsBeanX groupItem : beanXList) {
            SpecialGroupViewModel groupViewModel = new SpecialGroupViewModel(context);
            groupViewModel.groupName.set(groupItem.getName());
            items.add(groupViewModel);

            List<SpecialBean.ItemsBeanX.ItemsBean> beanList = groupItem.getItems();
            if (beanList.size() > 0) {
                for (SpecialBean.ItemsBeanX.ItemsBean childItem : beanList) {
                    SpecialChildViewModel childViewModel = new SpecialChildViewModel(context);
                    childViewModel.childName.set(childItem.getTitle());
                    childViewModel.dateText.set(DateUtils.Date2Str(new Date(childItem.getTime()), "MM月dd日"));
                    items.add(childViewModel);
                }
            }
        }
    }
}
