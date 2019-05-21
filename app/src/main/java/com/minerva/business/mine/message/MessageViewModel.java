package com.minerva.business.mine.message;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.message.model.MsgListBean;
import com.minerva.business.mine.message.model.MsgModel;
import com.minerva.common.BlankViewModel;
import com.minerva.common.Constants;
import com.minerva.common.RefreshListViewModel;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtil;
import com.minerva.utils.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class MessageViewModel extends RefreshListViewModel {
    public ObservableField<String> mTitle = new ObservableField<>(ResourceUtil.getString(R.string.mine_notification));
    public ObservableList<BaseViewModel> items = new ObservableArrayList<>();
    public OnItemBind<BaseViewModel> msgItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            switch (item.getViewType()) {
                case Constants.RecyclerItemType.MY_MESSAGE_ITEM_TYPE:
                    itemBinding.set(BR.msgItemVM, R.layout.item_message_layout);
                    break;
                case Constants.RecyclerItemType.BLANK_TYPE:
                    itemBinding.set(BR.vmBlank, R.layout.item_blank_layout);
                    break;
            }
        }
    };
    private BlankViewModel mBlankVM;
    private List<MsgListBean.ItemsBean> itemsBeanList = new ArrayList<>();
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };

    MessageViewModel(Context context) {
        super(context);
        requestServer();
    }

    public SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshing.set(true);
            requestServer();
        }
    };

    @Override
    protected void requestServer() {
        if (!CommonUtil.isNetworkAvailable(context)) {
            refreshing.set(false);
            if (mBlankVM == null) {
                mBlankVM = new BlankViewModel(context);
            }
            mBlankVM.setStatus(Constants.PageStatus.NETWORK_EXCEPTION);
            items.add(mBlankVM);
            return;
        }

        refreshing.set(true);
        MsgModel.getInstance().getMessageList(new NetworkObserver<MsgListBean>() {
            @Override
            public void onSuccess(MsgListBean msgListBean) {
                refreshing.set(false);
                itemsBeanList.clear();
                itemsBeanList.addAll(msgListBean.getItems());
                createViewModel();
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    @Override
    protected void createViewModel() {
        items.clear();
        for (MsgListBean.ItemsBean itemBean : itemsBeanList) {
            if (itemBean == null) {
                continue;
            }

            MessageItemViewModel itemViewModel = new MessageItemViewModel(context);
            switch (itemBean.getId()) {
                case 1:
                    itemViewModel.itemIcon.set(R.drawable.icon_system_notification);
                    break;
                case 3:
                    itemViewModel.itemIcon.set(R.drawable.icon_at);
                    break;
                case 5:
                    itemViewModel.itemIcon.set(R.drawable.icon_active_notification);
                    break;
                case 6:
                    itemViewModel.itemIcon.set(R.drawable.icon_discuss);
                    break;
            }
            itemViewModel.itemName.set(itemBean.getName());
            items.add(itemViewModel);
        }
    }
}
