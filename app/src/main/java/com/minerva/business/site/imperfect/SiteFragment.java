package com.minerva.business.site.imperfect;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseBean;
import com.minerva.business.home.HomeActivity;
import com.minerva.business.site.menu.ChildMenuViewModel;
import com.minerva.business.site.menu.GroupMenuViewModel;
import com.minerva.business.site.menu.IPopupMenuItemClickListener;
import com.minerva.business.site.model.SiteModel;
import com.minerva.business.site.model.SitesBean;
import com.minerva.common.GlobalData;
import com.minerva.common.MinervaLinearLayoutManager;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtils;
import com.minerva.utils.DisplayUtils;
import com.minerva.utils.ResourceUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class SiteFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ItemLongClickListener, IPopupMenuItemClickListener {
    public static final String TYPE_GROUP = "group";
    public static final String TYPE_CHILD = "child";
    private View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<SitesBean.ItemsBeanX> mList = new ArrayList<>();
    private SiteAdapter mAdapter;
    private PopupWindow menuPopup;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = View.inflate(getActivity(), R.layout.fragment_site_layout, null);
        EventBus.getDefault().register(this);

        initView();
        requestServer();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(SitesBean sitesBean) {
        if (sitesBean != null) {
            mList.clear();
            mList.addAll(sitesBean.getItems());
            SiteModel.getInstance().setItemList(sitesBean.getItems());
            notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(BaseBean baseBean) {
        if (baseBean != null && baseBean.isSuccess()) {
            requestServer();
        }
    }

    @Override
    public void onRefresh() {
        requestServer();
    }

    @Override
    public void onGroupLongClick(int groupId, String name) {
        if (GlobalData.getInstance().isLogin()) {
            showPopupMenu(TYPE_GROUP, groupId, "", name);
        }
    }

    @Override
    public void onChildLongClick(int groupId, String child) {
        if (GlobalData.getInstance().isLogin()) {
            showPopupMenu(TYPE_CHILD, groupId, child, "");
        }
    }

    @Override
    public void onMenuItemClick() {
        if (menuPopup != null) {
            menuPopup.dismiss();
        }
    }

    private void requestServer() {
        swipeRefreshLayout.setRefreshing(true);
        SiteModel.getInstance().getSiteList(new NetworkObserver<SitesBean>() {
            @Override
            public void onSuccess(SitesBean sitesBean) {
                swipeRefreshLayout.setRefreshing(false);
                mList.clear();
                mList.addAll(sitesBean.getItems());
                SiteModel.getInstance().setItemList(sitesBean.getItems());
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(String msg) {
                swipeRefreshLayout.setRefreshing(false);
                mList = SiteModel.getInstance().loadLocalData();
                notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new MinervaLinearLayoutManager(getActivity()));
        mAdapter = new SiteAdapter(getActivity(), mList, this);
        recyclerView.setAdapter(mAdapter);
    }


    private void notifyDataSetChanged() {
        if (mAdapter != null) {
            mAdapter.setList(mList);
            mAdapter.notifyDataSetChanged();
        }
    }

    private void showPopupMenu(String type, int groupId, String childId, String name) {
        if (menuPopup == null) {
            menuPopup = new PopupWindow(getActivity().getWindow().getDecorView(), DisplayUtils.getScreenWidth() * 3 / 4, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            menuPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                    lp.alpha = 1f;
                    getActivity().getWindow().setAttributes(lp);
                }
            });
        }
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.6f;
        getActivity().getWindow().setAttributes(lp);

        ViewDataBinding binding = null;
        if (TextUtils.equals(type, TYPE_GROUP)) {
            GroupMenuViewModel viewModel = new GroupMenuViewModel(getActivity(), this);
            viewModel.isEditMenuGone.set(groupId == 0);
            viewModel.setGroupId(groupId);
            viewModel.setName(name);
            binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_group_menu_layout, null, false);
            binding.setVariable(BR.groupMenuVM, viewModel);
        }

        if (TextUtils.equals(type, TYPE_CHILD)) {
            ChildMenuViewModel viewModel = new ChildMenuViewModel(getActivity(), this);
            viewModel.setGroupId(groupId);
            viewModel.setChildId(childId);
            binding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.dialog_child_menu_layout, null, false);
            binding.setVariable(BR.childMenuVM, viewModel);
        }

        if (binding != null) {
            binding.executePendingBindings();
            menuPopup.setContentView(binding.getRoot());
        }
        menuPopup.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

}
