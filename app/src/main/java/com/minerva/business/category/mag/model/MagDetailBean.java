package com.minerva.business.category.mag.model;

import com.minerva.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class MagDetailBean extends BaseBean {

    private List<ItemsBeanX> items = new ArrayList<>();

    public List<ItemsBeanX> getItems() {
        return items;
    }

    public void setItems(List<ItemsBeanX> items) {
        this.items = items;
    }

    public static class ItemsBeanX {
        /**
         * name : 前端开发
         * items : [{"url":"2mIfAnu","title":"了解这 12 个概念，让你的 JavaScript 水平更上一层楼","meta":"InfoQ","type":0},{"url":"IfYRJjA","title":"不可思议的纯 CSS 实现鼠标跟随效果","meta":"ChokCoco","type":0},{"url":"3QZriiN","title":"前端进击的巨人（八）：浅谈函数防抖与节流","meta":"SegmentFault","type":0},{"url":"qE3EFb6","title":"前端水印初探","meta":"大搜车产品技术设计团队","type":0},{"url":"ErQjeaf","title":"前端为什么要会正则表达式","meta":"饿了么前端","type":0},{"url":"NJBrMn2","title":"判断元素是否在视窗之内","meta":"IMWeb","type":0},{"url":"muAf6rQ","title":"Vue 性能优化：如何实现延迟加载和代码拆分？","meta":"InfoQ","type":0}]
         */

        private String name;
        private List<ItemsBean> items = new ArrayList<>();

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

        public static class ItemsBean {
            /**
             * url : 2mIfAnu
             * title : 了解这 12 个概念，让你的 JavaScript 水平更上一层楼
             * meta : InfoQ
             * type : 0
             */

            private String url;
            private String title;
            private String meta;
            private int type;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getMeta() {
                return meta;
            }

            public void setMeta(String meta) {
                this.meta = meta;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }
    }
}
