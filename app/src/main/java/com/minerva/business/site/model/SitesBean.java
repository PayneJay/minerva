package com.minerva.business.site.model;

import com.hgdendi.expandablerecycleradapter.BaseExpandableRecyclerViewAdapter;
import com.minerva.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class SitesBean extends BaseBean {

    private List<ItemsBeanX> items = new ArrayList<>();

    public List<ItemsBeanX> getItems() {
        return items;
    }

    public void setItems(List<ItemsBeanX> items) {
        this.items = items;
    }

    public static class ItemsBeanX implements BaseExpandableRecyclerViewAdapter.BaseGroupBean<ItemsBeanX.ItemsBean> {
        /**
         * id : 4
         * name : 科技媒体
         * items : [{"id":"uInaqm","name":"创见","image":"https://stimg1.tuicool.com/uInaqm.png","cnt":0,"followed":true,"time":1551666783000},{"id":"RNBVBj","name":"创业邦","image":"https://stimg1.tuicool.com/RNBVBj.png","cnt":0,"followed":true,"time":1551684267258},{"id":"QFVjIj","name":"虎嗅网","image":"https://stimg2.tuicool.com/QFVjIj.png","cnt":0,"followed":true,"time":1551686715726},{"id":"m2qMVf","name":"PingWest","image":"https://stimg1.tuicool.com/m2qMVf.png","cnt":0,"followed":true,"time":1551681368113},{"id":"aaMFJj","name":"动点科技","image":"https://stimg1.tuicool.com/aaMFJj.png","cnt":0,"followed":true,"time":1551688368000},{"id":"MJNVfm","name":"36氪","image":"https://stimg2.tuicool.com/MJNVfm.png","cnt":0,"followed":true,"time":1551690326848},{"id":"AF3qmi","name":"钛媒体","image":"https://stimg1.tuicool.com/AF3qmi.png","cnt":0,"followed":true,"time":1551689160000},{"id":"mY3iae","name":"猎云网","image":"https://stimg1.tuicool.com/mY3iae.png","cnt":0,"followed":true,"time":1551683164000},{"id":"22yAFr","name":"i黑马","image":"https://stimg1.tuicool.com/22yAFr.png","cnt":0,"followed":true,"time":1551690296134},{"id":"AJj2Un","name":"亿欧网","image":"https://stimg1.tuicool.com/AJj2Un.png","cnt":0,"followed":true,"time":1551690060000}]
         */

        private int id;
        private String name;
        private List<ItemsBean> items = new ArrayList<>();

        public ItemsBeanX(List<ItemsBean> childList, String name) {
            this.items.clear();
            this.items.addAll(childList);
            this.name = name;
        }

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

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        @Override
        public int getChildCount() {
            return items.size();
        }

        @Override
        public ItemsBean getChildAt(int childIndex) {
            return items.size() <= childIndex ? null : items.get(childIndex);
        }

        @Override
        public boolean isExpandable() {
            return getChildCount() > 0;
        }

        public static class ItemsBean {
            /**
             * id : uInaqm
             * name : 创见
             * image : https://stimg1.tuicool.com/uInaqm.png
             * cnt : 0
             * followed : true
             * time : 1551666783000
             */

            private String id;
            private String name;
            private String image;
            private int cnt;
            private boolean followed;
            private long time;

            public ItemsBean(ItemsBean itemsBean) {
                this.id = itemsBean.id;
                this.name = itemsBean.name;
                this.image = itemsBean.image;
                this.cnt = itemsBean.cnt;
                this.followed = itemsBean.followed;
                this.time = itemsBean.time;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getCnt() {
                return cnt;
            }

            public void setCnt(int cnt) {
                this.cnt = cnt;
            }

            public boolean isFollowed() {
                return followed;
            }

            public void setFollowed(boolean followed) {
                this.followed = followed;
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
