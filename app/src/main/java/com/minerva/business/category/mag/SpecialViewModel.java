package com.minerva.business.category.mag;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.business.category.mag.model.MagModel;
import com.minerva.business.category.model.MagBean;
import com.minerva.business.category.model.SpecialModel;
import com.minerva.common.BlankViewModel;
import com.minerva.common.Constants;
import com.minerva.common.IPageStateListener;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class SpecialViewModel extends BaseViewModel implements IPageStateListener {
    public ObservableBoolean refreshing = new ObservableBoolean();
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
    private List<MagBean.ItemsBeanX> beanXList = new ArrayList<>();
    private BlankViewModel mBlankVM;

    SpecialViewModel(Context context) {
        super(context);
        requestServer();
    }

    public int[] getColors() {
        return new int[]{R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark};
    }

    public ObservableList<BaseViewModel> getItems() {
        return MagModel.getInstance().getData();
    }

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            requestServer();
        }
    };


    @Override
    public void setPageByState(int state) {
        if (mBlankVM == null) {
            mBlankVM = new BlankViewModel(context);
        }
        mBlankVM.setStatus(state);
        MagModel.getInstance().clear();
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        temp.add(mBlankVM);
        MagModel.getInstance().setData(temp);
    }

    private void requestServer() {
        refreshing.set(true);
        if (!CommonUtils.isNetworkAvailable(context)) {
            refreshing.set(false);
            setPageByState(Constants.PageStatus.NETWORK_EXCEPTION);
            return;
        }
        SpecialModel.getInstance().getSpecialList(new NetworkObserver<MagBean>() {
            @Override
            public void onSuccess(MagBean magBean) {
                refreshing.set(false);
                beanXList.clear();
                beanXList.addAll(magBean.getItems());
                createViewModel();
            }

            @Override
            public void onFailure(String msg) {
                refreshing.set(false);
                createViewModel();
            }
        });
    }

    private void createViewModel() {
        if (beanXList.size() <= 0) {
            setPageByState(Constants.PageStatus.NO_DATA);
            return;
        }

        MagModel.getInstance().setData(getObservableList());
    }

    private ObservableList<BaseViewModel> getObservableList() {
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        for (MagBean.ItemsBeanX groupItem : beanXList) {
            SpecialGroupViewModel groupViewModel = new SpecialGroupViewModel(context);
            groupViewModel.groupName.set(groupItem.getName());
            groupViewModel.type = groupItem.getType();
            groupViewModel.tabType = Constants.CategoryTabType.TAB_MAG;
            groupViewModel.setId(groupItem.getType() + "");
            temp.add(groupViewModel);

            List<MagBean.ItemsBeanX.ItemsBean> beanList = groupItem.getItems();
            if (beanList.size() > 0) {
                for (MagBean.ItemsBeanX.ItemsBean childItem : beanList) {
                    SpecialChildViewModel childViewModel = new SpecialChildViewModel(context);
                    childViewModel.childName.set(childItem.getTitle());
                    childViewModel.groupName = groupItem.getName();
                    childViewModel.magID = childItem.getId();
                    childViewModel.type = childItem.getType();
                    childViewModel.setDate(childItem.getTime());
                    temp.add(childViewModel);
                }
            }
        }
        return temp;
    }

}
