package com.minerva.business.category.model;

import com.minerva.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class BookBean extends BaseBean {

    private List<ItemsBean> items = new ArrayList<>();

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * books : [{"id":"5c60327ea74e6482ccf31a28","link":"https://u.jd.com/pz5wLD","title":"Django企业开发实战 高效Python Web框架指南","thumb":"https://img12.360buyimg.com/n1/jfs/t1/30017/36/1502/96268/5c4fd231E7efabaf1/51c5070ab184e2b8.jpg","author":"胡阳","price":99,"pubTime":1548950400001},{"id":"5c4b0089e5f6db5319f4df6b","link":"https://u.jd.com/JAowlm","title":"大型企业微服务架构实践与运营","thumb":"https://img11.360buyimg.com/n1/jfs/t1/11016/34/6180/628718/5c3c12cfEf0018fcf/efba2b9872a42316.jpg","author":"薛浩","price":88,"pubTime":1548419209109},{"id":"5c4b0086e5f6db5319f4df69","link":"https://u.jd.com/jqmbR6","title":"深度学习 智能时代的核心驱动力量 ","thumb":"https://img14.360buyimg.com/n1/jfs/t1/11012/21/5845/228363/5c3707e5Efbcce250/254fd1b2927ca7ce.jpg","author":"[美]特伦斯·谢诺夫斯基,Terrence Sejnowski","price":88,"pubTime":1548419206240}]
         * tagId : 0
         * tagName : 最新图书
         */

        private int tagId;
        private String tagName;
        private List<BooksBean> books = new ArrayList<>();

        public int getTagId() {
            return tagId;
        }

        public void setTagId(int tagId) {
            this.tagId = tagId;
        }

        public String getTagName() {
            return tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }

        public List<BooksBean> getBooks() {
            return books;
        }

        public void setBooks(List<BooksBean> books) {
            this.books = books;
        }

        public static class BooksBean {
            /**
             * id : 5c60327ea74e6482ccf31a28
             * link : https://u.jd.com/pz5wLD
             * title : Django企业开发实战 高效Python Web框架指南
             * thumb : https://img12.360buyimg.com/n1/jfs/t1/30017/36/1502/96268/5c4fd231E7efabaf1/51c5070ab184e2b8.jpg
             * author : 胡阳
             * price : 99.0
             * pubTime : 1548950400001
             */

            private String id;
            private String link;
            private String title;
            private String thumb;
            private String author;
            private double price;
            private long pubTime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getThumb() {
                return thumb;
            }

            public void setThumb(String thumb) {
                this.thumb = thumb;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public long getPubTime() {
                return pubTime;
            }

            public void setPubTime(long pubTime) {
                this.pubTime = pubTime;
            }
        }
    }
}
