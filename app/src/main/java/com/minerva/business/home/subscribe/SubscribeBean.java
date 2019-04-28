package com.minerva.business.home.subscribe;

import com.minerva.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class SubscribeBean extends BaseBean {
    private int id;
    private List<ItemsBean> items;
    private List<NaviBean> navi;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ItemsBean> getItems() {
        if (items == null) {
            return new ArrayList<>();
        }
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public List<NaviBean> getNavi() {
        if (navi == null) {
            return new ArrayList<>();
        }
        return navi;
    }

    public void setNavi(List<NaviBean> navi) {
        this.navi = navi;
    }

    public static class ItemsBean {
        /**
         * id : MBFFzaA
         * followed : false
         * name : 创宇前端
         * image : https://stimg2.tuicool.com/MBFFzaA.png!middle
         * lang : 1
         * st : 0
         * cover : https://aimg1.tuicool.com/BZFruur.jpg
         */

        private String id;
        private boolean followed;
        private String name;
        private String image;
        private int lang;
        private int st;
        private String cover;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isFollowed() {
            return followed;
        }

        public void setFollowed(boolean followed) {
            this.followed = followed;
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

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }

    public static class NaviBean {
        /**
         * id : 1
         * name : 新新推荐
         * image : http://static0.tuicool.com/images/hot_site/1.jpg
         */

        private int id;
        private String name;
        private String image;
        //自定义参数，表示是否选择
        private boolean isSelected;

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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
