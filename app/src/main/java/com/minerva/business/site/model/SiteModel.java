package com.minerva.business.site.model;


import com.minerva.business.article.list.model.ArticleBean;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;
import com.minerva.utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SiteModel {
    private static SiteModel instance;

    private SiteModel() {
    }

    public static SiteModel getInstance() {
        if (instance == null) {
            instance = new SiteModel();
        }
        return instance;
    }

    public List<SitesBean.ItemsBeanX> generateData() {
        SitesBean bean = CommonUtils.getSiteListFromJson();

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

    /**
     * 获取站点信息列表
     *
     * @param observer 回调
     */
    public void getSiteList(Observer<? super SitesBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getSiteList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 获取聚合阅读列表
     *
     * @param id       聚合id
     * @param page     请求第几页
     * @param code     code
     * @param observer 回调
     */
    public void getPolymerReadList(int id, int page, String code, Observer<? super PolymerRead> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_GET, null)
                .getServer()
                .getPolymerReadList(id, page, code, -1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
