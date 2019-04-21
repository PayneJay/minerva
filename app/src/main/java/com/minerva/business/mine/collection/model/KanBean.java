package com.minerva.business.mine.collection.model;

import com.minerva.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class KanBean extends BaseBean {

    private List<ItemsBean> items = new ArrayList<>();

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * id : 1790233527
         * ut : 1553335195000
         * name : minerva
         * n : minerva
         * img : http://img0.tuicool.com/ueI77nY.jpg!kan
         * desc :
         * type : 0
         * ac : 2
         */

        private String id;
        private long ut;
        private String name;
        private String n;
        private String img;
        private String desc;
        private int type;
        private int ac;
        private boolean isSelected;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public long getUt() {
            return ut;
        }

        public void setUt(long ut) {
            this.ut = ut;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getN() {
            return n;
        }

        public void setN(String n) {
            this.n = n;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getAc() {
            return ac;
        }

        public void setAc(int ac) {
            this.ac = ac;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
