package com.minerva.business.category.model;

import com.minerva.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class SpecialBean extends BaseBean {

    private List<ItemsBeanX> items = new ArrayList<>();

    public List<ItemsBeanX> getItems() {
        return items;
    }

    public void setItems(List<ItemsBeanX> items) {
        this.items = items;
    }

    public static class ItemsBeanX {
        /**
         * name : 编程狂人
         * type : 0
         * items : [{"id":"5c6f816b386bba1d38aad18d","type":0,"title":"第二六二期","time":1550839807000},{"id":"5c53d6a2386bba27c4a0c676","type":0,"title":"第二六一期","time":1548999782000},{"id":"5c4ac1db386bba27f2a0a0db","type":0,"title":"第二六零期","time":1548419458000},{"id":"5c4164d2386bba27baa0905b","type":0,"title":"第二五九期","time":1547790448000},{"id":"5c38267a386bba302105e2d9","type":0,"title":"第二五八期","time":1547185498000}]
         */

        private String name;
        private int type;
        private List<ItemsBean> items = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * id : 5c6f816b386bba1d38aad18d
             * type : 0
             * title : 第二六二期
             * time : 1550839807000
             */

            private String id;
            private int type;
            private String title;
            private long time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }
        }
    }
}
