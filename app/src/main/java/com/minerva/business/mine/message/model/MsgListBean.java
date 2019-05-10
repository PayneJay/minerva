package com.minerva.business.mine.message.model;

import com.minerva.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class MsgListBean extends BaseBean {

    /**
     * items : [{"id":1,"name":"icon_system_notification","count":0},{"id":5,"name":"icon_active_notification","count":0},{"id":3,"name":"@我的","count":0},{"id":6,"name":"icon_discuss","count":0}]
     * total_unread : 0
     */

    private int total_unread;
    private List<ItemsBean> items = new ArrayList<>();

    public int getTotal_unread() {
        return total_unread;
    }

    public void setTotal_unread(int total_unread) {
        this.total_unread = total_unread;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * id : 1
         * name : icon_system_notification
         * count : 0
         */

        private int id;
        private String name;
        private int count;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
