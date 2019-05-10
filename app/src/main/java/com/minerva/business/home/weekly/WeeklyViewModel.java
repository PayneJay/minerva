package com.minerva.business.home.weekly;

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
import com.minerva.business.home.weekly.model.WeeklyModel;
import com.minerva.business.home.weekly.model.WeekListBean;
import com.minerva.common.BlankViewModel;
import com.minerva.common.Constants;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class WeeklyViewModel extends BaseViewModel {
    public ObservableBoolean refreshing = new ObservableBoolean();
    public OnItemBind<BaseViewModel> weeklyItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            if (item instanceof WeeklyItemViewModel) {
                itemBinding.set(BR.weekItemVM, R.layout.item_weekly_layout);
            }
            if (item instanceof BlankViewModel) {
                itemBinding.set(BR.vmBlank, R.layout.item_blank_layout);
            }
        }
    };
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };
    private BlankViewModel mBlankVM;
    private List<WeekListBean.ItemsBean> mItems = new ArrayList<>();

    WeeklyViewModel(Context context) {
        super(context);
        requestServer();
    }

    public int[] getColors() {
        return new int[]{R.color.color_1E90FF, R.color.color_FF77FF, R.color.color_00AEAE};
    }

    public ObservableList<BaseViewModel> getItems() {
        return WeeklyModel.getInstance().getData();
    }

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            requestServer();
        }
    };

    private void requestServer() {
        refreshing.set(true);
        if (!CommonUtils.isNetworkAvailable(context)) {
            refreshing.set(false);
            setErrorPage(Constants.PageStatus.NETWORK_EXCEPTION);
            return;
        }

        WeeklyModel.getInstance().getWeeklyList(new NetworkObserver<WeekListBean>() {
            @Override
            public void onSuccess(WeekListBean weekListBean) {
                refreshing.set(false);
                mItems.clear();
                mItems.addAll(weekListBean.getItems());
                WeeklyModel.getInstance().setData(getObserverList());
            }

            @Override
            public void onFailure(String msg) {
                refreshing.set(false);
                setErrorPage(Constants.PageStatus.NO_DATA);
            }
        });
    }

    private void setErrorPage(int state) {
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        if (mBlankVM == null) {
            mBlankVM = new BlankViewModel(context);
        }
        WeeklyModel.getInstance().clear();
        mBlankVM.setStatus(state);
        temp.add(mBlankVM);
        WeeklyModel.getInstance().setData(temp);
    }

    private ObservableList<BaseViewModel> getObserverList() {
        ObservableList<BaseViewModel> temp = new ObservableArrayList<>();
        if (mItems.size() <= 0) {
            if (mBlankVM == null) {
                mBlankVM = new BlankViewModel(context);
            }
            WeeklyModel.getInstance().clear();
            mBlankVM.setStatus(Constants.PageStatus.NO_DATA);
            temp.add(mBlankVM);
        }

        for (WeekListBean.ItemsBean item : mItems) {
            if (item == null) {
                continue;
            }
            temp.add(new WeeklyItemViewModel(context, item));
        }
        return temp;
    }
}
