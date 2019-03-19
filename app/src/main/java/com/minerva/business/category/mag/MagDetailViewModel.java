package com.minerva.business.category.mag;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.category.mag.model.MagDetailBean;
import com.minerva.business.category.mag.model.MagModel;
import com.minerva.common.BlankViewModel;
import com.minerva.common.Constants;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class MagDetailViewModel extends BaseViewModel {
    public ObservableField<String> title = new ObservableField<>();
    public ObservableBoolean refreshing = new ObservableBoolean();
    public ObservableList<BaseViewModel> items = new ObservableArrayList<>();
    public OnItemBind<BaseViewModel> magItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            switch (item.getViewType()) {
                case Constants.RecyclerItemType.MAG_TITLE_TYPE:
                    itemBinding.set(BR.magTitleVM, R.layout.item_mag_title_layout);
                    break;
                case Constants.RecyclerItemType.SPECIAL_GROUP_TYPE:
                    itemBinding.set(BR.specialGroupVM, R.layout.item_special_group_layout);
                    break;
                case Constants.RecyclerItemType.MAG_CHILD_TYPE:
                    itemBinding.set(BR.specialChildVM, R.layout.item_special_child_layout);
                    break;
                case Constants.RecyclerItemType.BLANK_TYPE:
                    itemBinding.set(BR.vmBlank, R.layout.item_blank_layout);
                    break;
            }
        }
    };
    public View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };
    public RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            if (position > 0) {
                title.set(name);
            } else {
                title.set("");
            }
        }
    };
    private BlankViewModel mBlankVM;
    private List<MagDetailBean.ItemsBeanX> mData = new ArrayList<>();
    private String magID, name;
    private int type;

    MagDetailViewModel(Context context) {
        super(context);
        magID = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.COLUMN_MAG_ID);
        name = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.COLUMN_MAG_NUMBER);
        type = ((BaseActivity) context).getIntent().getIntExtra(Constants.KeyExtra.COLUMN_MAG_TYPE, 0);

        MagTitleViewModel titleViewModel = new MagTitleViewModel(context);
        String randomTitle = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.COLUMN_MAG_TITLE);
        if (!TextUtils.isEmpty(randomTitle)) {
            titleViewModel.title.set(randomTitle.substring(0, 1));
        }
        titleViewModel.name.set(name);
        items.add(titleViewModel);

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

    public void loadMore() {
        Log.e(Constants.TAG, "loadMore...");
    }

    private void requestServer() {
        if (!CommonUtils.isNetworkAvailable(context)) {
            refreshing.set(false);
            if (mBlankVM == null) {
                mBlankVM = new BlankViewModel(context);
            }
            removeExcludeTitle();
            mBlankVM.setStatus(Constants.PageStatus.NETWORK_EXCEPTION);
            items.add(mBlankVM);
            return;
        }

        MagModel.getInstance().getMagDetail(magID, type, new NetworkObserver<MagDetailBean>() {
            @Override
            public void onSuccess(MagDetailBean magDetailBean) {
                refreshing.set(false);
                mData.clear();
                mData.addAll(magDetailBean.getItems());
                createViewModel();
            }

            @Override
            public void onFailure() {
                refreshing.set(false);
            }
        });
    }

    private void createViewModel() {
        if (mData.size() <= 0) {
            return;
        }
        removeExcludeTitle();

        for (MagDetailBean.ItemsBeanX groupItem : mData) {
            SpecialGroupViewModel groupViewModel = new SpecialGroupViewModel(context);
            groupViewModel.groupName.set(groupItem.getName());
            groupViewModel.menuName.set("");
            items.add(groupViewModel);

            List<MagDetailBean.ItemsBeanX.ItemsBean> childItems = groupItem.getItems();
            if (childItems.size() > 0) {
                for (MagDetailBean.ItemsBeanX.ItemsBean childItem : childItems) {
                    MagChildViewModel childViewModel = new MagChildViewModel(context);
                    childViewModel.childName.set(childItem.getTitle());
                    childViewModel.articleID = childItem.getUrl();
                    items.add(childViewModel);
                }
            }
        }
    }

    /**
     * 移除除Title外的item
     */
    private void removeExcludeTitle() {
        Iterator<BaseViewModel> iterator = items.iterator();
        while (iterator.hasNext()) {
            BaseViewModel viewModel = iterator.next();
            if (Constants.RecyclerItemType.MAG_TITLE_TYPE != viewModel.getViewType()) {
                iterator.remove();
            }
        }
    }
}
