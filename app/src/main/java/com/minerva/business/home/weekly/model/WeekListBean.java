package com.minerva.business.home.weekly.model;

import com.minerva.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class WeekListBean extends BaseBean {

    private List<ItemsBean> items;

    public List<ItemsBean> getItems() {
        if (items == null) {
            return new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * id : 5cbbe4a06d6beb796448c6a2
         * time : 1555817632597
         */

        private String id;
        private long time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }
    }
}
