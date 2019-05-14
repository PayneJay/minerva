package com.minerva.business.category.mag.model;

import com.minerva.base.BaseBean;
import com.minerva.business.category.model.MagBean;

import java.util.ArrayList;
import java.util.List;

public class MagPeriodBean extends BaseBean {

    private List<MagBean.ItemsBeanX.ItemsBean> items = new ArrayList<>();

    public List<MagBean.ItemsBeanX.ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<MagBean.ItemsBeanX.ItemsBean> items) {
        this.items = items;
    }
}
