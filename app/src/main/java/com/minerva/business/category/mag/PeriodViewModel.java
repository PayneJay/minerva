package com.minerva.business.category.mag;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.category.mag.model.MagListModel;
import com.minerva.business.category.mag.model.MagModel;
import com.minerva.business.category.mag.model.MagPeriodBean;
import com.minerva.business.category.model.MagBean;
import com.minerva.common.RefreshListViewModel;
import com.minerva.common.Constants;
import com.minerva.common.IPageStateListener;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class PeriodViewModel extends RefreshListViewModel implements IPageStateListener {
    public OnItemBind<BaseViewModel> periodItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            switch (item.getViewType()) {
                case Constants.RecyclerItemType.SPECIAL_CHILD_TYPE:
                    itemBinding.set(BR.specialChildVM, R.layout.item_special_child_layout);
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
    public String mTitle;
    private List<MagBean.ItemsBeanX.ItemsBean> beanList = new ArrayList<>();
    private int mType;

    PeriodViewModel(Context context) {
        super(context);
        mType = ((BaseActivity) context).getIntent().getIntExtra(Constants.KeyExtra.COLUMN_MAG_TYPE, 0);
        mTitle = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.COLUMN_MAG_TITLE);
        requestServer();
    }

    public ObservableList<BaseViewModel> getItems() {
        return MagListModel.getInstance().getData();
    }

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshing.set(true);
            requestServer();
        }
    };

    @Override
    public void setPageByState(int state) {

    }

    @Override
    protected void requestServer() {
        if (!CommonUtil.isNetworkAvailable(context)) {
            refreshing.set(false);
            setPageByState(Constants.PageStatus.NETWORK_EXCEPTION);
            return;
        }

        MagModel.getInstance().getMagPeriodList(mType, new NetworkObserver<MagPeriodBean>() {
            @Override
            public void onSuccess(MagPeriodBean magPeriod) {
                refreshing.set(false);
                beanList.clear();
                beanList.addAll(magPeriod.getItems());
                createViewModel();
            }

            @Override
            public void onFailure(String msg) {
                refreshing.set(false);
            }
        });
    }

    @Override
    protected void createViewModel() {
        if (beanList.size() <= 0) {
            setPageByState(Constants.PageStatus.NO_DATA);
            return;
        }

        MagListModel.getInstance().setData(getObservableList());
    }

    private ObservableList<BaseViewModel> getObservableList() {
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        for (MagBean.ItemsBeanX.ItemsBean childItem : beanList) {
            SpecialChildViewModel childViewModel = new SpecialChildViewModel(context);
            childViewModel.childName.set(childItem.getTitle());
            childViewModel.groupName = mTitle;
            childViewModel.magID = childItem.getId();
            childViewModel.type = childItem.getType();
            childViewModel.setDate(childItem.getTime());
            temp.add(childViewModel);
        }
        return temp;
    }
}
