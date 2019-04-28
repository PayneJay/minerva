package com.minerva.business.site.detail;

import com.minerva.base.BaseBean;
import com.minerva.business.article.list.model.ArticleBean;

import java.util.ArrayList;
import java.util.List;

public class SiteDetailBean extends BaseBean {

    /**
     * articles : [{"id":"j2qy6bV","title":"基于色键技术的纯客户端实时蒙版弹幕","time":"01-23 11:34","rectime":"01-23 11:34","uts":1548214466000,"feed_title":"创宇前端","img":"https://aimg1.tuicool.com/BZFruur.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"Y3uUvqj","title":"ElasticSearch性能调优","time":"01-09 16:20","rectime":"01-09 16:20","uts":1547022025000,"feed_title":"创宇前端","img":"https://aimg0.tuicool.com/vyqe2yi.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"7VR7RzV","title":"异端审判器！一个泛用型文本聚类模型的实现（2）","time":"01-04 14:47","rectime":"01-04 14:47","uts":1546584461000,"feed_title":"创宇前端","img":"https://aimg0.tuicool.com/mAriyia.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"raANfiq","title":"也许你对Fetch了解的不是那么多（二)","time":"2018-12-29","rectime":"2018-12-29","uts":1546044067252,"feed_title":"创宇前端","img":"https://aimg2.tuicool.com/63yaYve.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"UJ3myib","title":"也许你对Fetch了解的不是那么多(一)","time":"2018-12-28","rectime":"2018-12-28","uts":1545970993000,"feed_title":"创宇前端","img":"https://aimg2.tuicool.com/63yaYve.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"bENZNz7","title":"No title","time":"2018-12-17","rectime":"2018-12-17","uts":1545021730889,"feed_title":"创宇前端","img":"https://aimg2.tuicool.com/vAN3q2Y.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"vy6Rjqr","title":"CSS 列表项布局技巧","time":"2018-12-13","rectime":"2018-12-13","uts":1544704052000,"feed_title":"创宇前端","img":"https://aimg2.tuicool.com/yuIJf2U.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"UrIvmiN","title":"人工智障也刷题！Kaggle入门之实战泰坦尼克号","time":"2018-12-04","rectime":"2018-12-04","uts":1543907224000,"feed_title":"创宇前端","img":"https://aimg0.tuicool.com/ABBv2eR.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"fmquIr","title":"【译】5 个最新的用户体验设计趋势","time":"2018-11-16","rectime":"2018-11-16","uts":1542361977000,"feed_title":"创宇前端","img":"https://aimg1.tuicool.com/IzYremi.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"vYZfeyn","title":"React Fiber","time":"2018-10-23","rectime":"2018-10-23","uts":1540285270000,"feed_title":"创宇前端","img":"https://aimg1.tuicool.com/Nj6VNvm.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"NVZnYvB","title":"【译】React Native与ios交互","time":"2018-10-22","rectime":"2018-10-22","uts":1540175257000,"feed_title":"创宇前端","img":"https://aimg1.tuicool.com/iY7naqm.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"UBR3QbN","title":"【译】你耳朵里有条鱼","time":"2018-10-11","rectime":"2018-10-11","uts":1539226521000,"feed_title":"创宇前端","img":"https://aimg0.tuicool.com/rAb2Qby.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"riIri2n","title":"区块链上编程：DApp 开发实践","time":"2018-10-08","rectime":"2018-10-08","uts":1538959252289,"feed_title":"创宇前端","img":"https://aimg0.tuicool.com/2qa2uef.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"amumayZ","title":"你听说过原生 HTML 组件吗？","time":"2018-10-05","rectime":"2018-10-05","uts":1538732087000,"feed_title":"创宇前端","img":"https://aimg2.tuicool.com/NVBB3ab.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"6bUv6jz","title":"用\u201c活着的\u201dCNN进行验证码识别","time":"2018-09-28","rectime":"2018-09-28","uts":1538104023000,"feed_title":"创宇前端","img":"https://aimg2.tuicool.com/yEFVzyZ.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"Ar2e2aA","title":"【译】超实用！7 个优秀的 UI 交互动画技巧","time":"2018-09-27","rectime":"2018-09-27","uts":1538040315000,"feed_title":"创宇前端","img":"https://aimg1.tuicool.com/RnqaYru.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"m22EFbi","title":"关于 HTTP2 的研究","time":"2018-09-25","rectime":"2018-09-25","uts":1537871508000,"feed_title":"创宇前端","img":"https://aimg1.tuicool.com/VnUFj2A.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"aMjIJfZ","title":"【译】Android 路径动画","time":"2018-09-25","rectime":"2018-09-25","uts":1537867584000,"feed_title":"创宇前端","img":"https://aimg0.tuicool.com/YziEfqJ.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"7jyQVv3","title":"异端审判器！一个泛用型文本聚类模型的实现（1）","time":"2018-09-25","rectime":"2018-09-25","uts":1537845294000,"feed_title":"创宇前端","img":"https://aimg0.tuicool.com/EZFNraj.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"7Jf2qiU","title":"我们应该做些力所能及的优化","time":"2018-09-25","rectime":"2018-09-25","uts":1537843592000,"feed_title":"创宇前端","img":"https://aimg1.tuicool.com/yaiI7rf.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"qUFBRz2","title":"【译】有哪些事情是编程 20 到 50 多年后才知道的？","time":"2018-09-25","rectime":"2018-09-25","uts":1537838835000,"feed_title":"创宇前端","img":"https://aimg1.tuicool.com/QZjABnz.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"VbyABzE","title":"认识 MongoDB 4.0 的新特性\u2014\u2014事务（Transactions）","time":"2018-09-24","rectime":"2018-09-24","uts":1537781102000,"feed_title":"创宇前端","img":"","abs":"","cmt":0,"st":0,"go":0},{"id":"AnMbYzE","title":"【译】与用户联系：在网页设计中融入幽默","time":"2018-09-19","rectime":"2018-09-19","uts":1537341977000,"feed_title":"创宇前端","img":"https://aimg1.tuicool.com/Fnquyay.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"RR3Erye","title":"【译】如何设计更好的数据表","time":"2018-09-19","rectime":"2018-09-19","uts":1537338111000,"feed_title":"创宇前端","img":"https://aimg1.tuicool.com/j6Frumr.jpg","abs":"","cmt":1,"st":0,"go":0},{"id":"jMvqIrB","title":"No title","time":"2018-09-16","rectime":"2018-09-16","uts":1537061805037,"feed_title":"创宇前端","img":"https://aimg0.tuicool.com/yaEFVnI.png","abs":"","cmt":0,"st":0,"go":0},{"id":"eq2Eny7","title":"一道事件循环题引发的血案","time":"2018-09-13","rectime":"2018-09-13","uts":1536799330000,"feed_title":"创宇前端","img":"https://aimg0.tuicool.com/e2Y7VbZ.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"Q7bqyai","title":"前端工程化：脚手架","time":"2018-09-12","rectime":"2018-09-12","uts":1536733455000,"feed_title":"创宇前端","img":"https://aimg0.tuicool.com/6z6f22U.png","abs":"","cmt":0,"st":0,"go":0},{"id":"QfqemmF","title":"浅谈 Vue 中 computed 实现原理","time":"2018-09-12","rectime":"2018-09-12","uts":1536720232000,"feed_title":"创宇前端","img":"https://aimg1.tuicool.com/ZNjeyaE.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"bueuI36","title":"Http的前世今生","time":"2018-09-11","rectime":"2018-09-11","uts":1536668019000,"feed_title":"创宇前端","img":"https://aimg2.tuicool.com/NZv2Ive.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"fyInu2M","title":"简述大数据实时处理框架","time":"2018-08-31","rectime":"2018-08-31","uts":1535698523000,"feed_title":"创宇前端","img":"https://aimg1.tuicool.com/JNrAbmv.jpg","abs":"","cmt":0,"st":0,"go":0}]
     * site : {"id":"MBFFzaA","followed":true,"name":"创宇前端","image":"https://stimg2.tuicool.com/MBFFzaA.png","lang":1,"st":0,"cover":"https://aimg1.tuicool.com/BZFruur.jpg"}
     * has_next : true
     */

    private SiteBean site;
    private boolean has_next;
    private List<ArticleBean.ArticlesBean> articles;

    public SiteBean getSite() {
        return site;
    }

    public void setSite(SiteBean site) {
        this.site = site;
    }

    public boolean isHas_next() {
        return has_next;
    }

    public void setHas_next(boolean has_next) {
        this.has_next = has_next;
    }

    public List<ArticleBean.ArticlesBean> getArticles() {
        if (articles == null) {
            return new ArrayList<>();
        }
        return articles;
    }

    public void setArticles(List<ArticleBean.ArticlesBean> articles) {
        this.articles = articles;
    }

    public static class SiteBean {
        /**
         * id : MBFFzaA
         * followed : true
         * name : 创宇前端
         * image : https://stimg2.tuicool.com/MBFFzaA.png
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
}
