package com.minerva.business.category.mag;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.category.mag.model.MagModel;
import com.minerva.business.category.mag.model.MagPeriod;
import com.minerva.business.category.model.MagBean;
import com.minerva.common.BlankViewModel;
import com.minerva.common.Constants;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtils;
import com.minerva.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class PeriodViewModel extends BaseViewModel {
    public ObservableBoolean refreshing = new ObservableBoolean();
    public ObservableList<BaseViewModel> items = new ObservableArrayList<>();
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
    private BlankViewModel mBlankVM;
    private int mType;

    PeriodViewModel(Context context) {
        super(context);
        mType = ((BaseActivity) context).getIntent().getIntExtra(Constants.KeyExtra.COLUMN_MAG_TYPE, 0);
        mTitle = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.COLUMN_MAG_TITLE);
        requestServer();
    }

    public int[] getColors() {
        return new int[]{R.color.colorPrimary, R.color.colorAccent, R.color.colorPrimaryDark};
    }

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshing.set(true);
            requestServer();
        }
    };

    private void requestServer() {
        if (!CommonUtils.isNetworkAvailable(context)) {
            refreshing.set(false);
            if (mBlankVM == null) {
                mBlankVM = new BlankViewModel(context);
            }
            mBlankVM.setStatus(Constants.PageStatus.NETWORK_EXCEPTION);
            items.clear();
            items.add(mBlankVM);
            return;
        }

        MagModel.getInstance().getMagPeriodList(mType, new NetworkObserver<MagPeriod>() {
            @Override
            public void onSuccess(MagPeriod magPeriod) {
                refreshing.set(false);
                beanList.clear();
                beanList.addAll(magPeriod.getItems());
                createViewModel();
            }

            @Override
            public void onFailure() {
                refreshing.set(false);
            }
        });
    }

    private void createViewModel() {
        if (beanList.size() <= 0) {
            if (mBlankVM != null) {
                mBlankVM.setStatus(Constants.PageStatus.NO_DATA);
            }
            return;
        }

        items.clear();
        for (MagBean.ItemsBeanX.ItemsBean childItem : beanList) {
            SpecialChildViewModel childViewModel = new SpecialChildViewModel(context);
            childViewModel.childName.set(childItem.getTitle());
            childViewModel.groupName = mTitle;
            childViewModel.magID = childItem.getId();
            childViewModel.type = childItem.getType();
            childViewModel.setDate(childItem.getTime());
            items.add(childViewModel);
        }
    }
}
