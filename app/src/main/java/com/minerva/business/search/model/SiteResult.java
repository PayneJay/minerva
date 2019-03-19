package com.minerva.business.search.model;

import com.minerva.base.BaseBean;
import com.minerva.business.site.model.SitesBean;

import java.util.ArrayList;
import java.util.List;

public class SiteResult extends BaseBean {
    private List<ResultItem> items = new ArrayList<>();

    public List<ResultItem> getItems() {
        return items;
    }

    public void setItems(List<ResultItem> items) {
        this.items = items;
    }

    public static class ResultItem extends SitesBean.ItemsBeanX.ItemsBean {
        private String cover;
        private int lang;
        private int st;

        public ResultItem(SitesBean.ItemsBeanX.ItemsBean itemsBean) {
            super(itemsBean);
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public int getLang() {
            return lang;
        }

        public void setLang(int lang) {
            this.lang = lang;
        }

        public int getSt() {
            return st;
        }

        public void setSt(int st) {
            this.st = st;
        }
    }
}
