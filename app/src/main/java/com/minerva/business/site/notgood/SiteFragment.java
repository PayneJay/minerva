package com.minerva.business.site.notgood;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minerva.R;
import com.minerva.business.site.model.SitesBean;
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
    private Disposable mDisposable;
    private List<SitesBean.ItemsBeanX> mList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = View.inflate(getActivity(), R.layout.fragment_site_layout, null);

        initView();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return rootView;
    }

    private void initView() {
        mList = generateData();
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new SiteAdapter(getActivity(), mList));
    }

    private List<SitesBean.ItemsBeanX> generateData() {
        SitesBean bean = CommonUtils.getSiteListFromJson("sites.json");

        List<SitesBean.ItemsBeanX> list = new ArrayList<>();
        for (int i = 0; i < bean.getItems().size(); i++) {
            final List<SitesBean.ItemsBeanX.ItemsBean> childList = new ArrayList<>(i);
            for (int j = 0; j < bean.getItems().get(i).getChildCount(); j++) {
                childList.add(new SitesBean.ItemsBeanX.ItemsBean(bean.getItems().get(i).getChildAt(j)));
            }
            list.add(new SitesBean.ItemsBeanX(childList, bean.getItems().get(i).getName()));
        }
        return list;
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        Observable.just("Success")
                //延时三秒，第一个参数是数值，第二个参数是事件单位
                .delay(3, TimeUnit.SECONDS)
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        swipeRefreshLayout.setRefreshing(false);
                        List<SitesBean.ItemsBeanX> list = generateData();
                        mList.clear();
                        mList.addAll(list);
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }
}
