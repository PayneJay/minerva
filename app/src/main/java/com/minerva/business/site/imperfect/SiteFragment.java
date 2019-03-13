package com.minerva.business.site.imperfect;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minerva.R;
import com.minerva.business.site.model.SiteModel;
import com.minerva.business.site.model.SitesBean;
import com.minerva.common.Constants;
import com.minerva.common.MinervaLinearLayoutManager;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SiteFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private List<SitesBean.ItemsBeanX> mList = new ArrayList<>();
    private SiteAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = View.inflate(getActivity(), R.layout.fragment_site_layout, null);

        initView();
        requestServer();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return rootView;
    }

    private void initView() {
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new MinervaLinearLayoutManager(getActivity()));
        mAdapter = new SiteAdapter(getActivity(), mList);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        requestServer();
    }

    private void requestServer() {
        swipeRefreshLayout.setRefreshing(true);
        SiteModel.getInstance().getSiteList(new NetworkObserver<SitesBean>() {
            @Override
            public void onSuccess(SitesBean sitesBean) {
                swipeRefreshLayout.setRefreshing(false);
                mList.clear();
                mList.addAll(sitesBean.getItems());
                notifyDataSetChanged();
            }

            @Override
            public void onFailure() {
                swipeRefreshLayout.setRefreshing(false);
                Log.i(Constants.TAG, "getSiteList failure");
                mList = SiteModel.getInstance().generateData();
                notifyDataSetChanged();
            }
        });
    }

    private void notifyDataSetChanged() {
        if (mAdapter != null) {
            mAdapter.setList(mList);
            mAdapter.notifyDataSetChanged();
        }
    }
}
