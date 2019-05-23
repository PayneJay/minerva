package com.minerva.business.article.list.model;

import com.minerva.base.BaseBean;
import com.minerva.business.article.detail.model.ArticleDetailBean;

import java.util.ArrayList;
import java.util.List;

public class ArticleListBean extends BaseBean {

    /**
     * has_next : true
     * articles : [{"id":"niiae26","title":"MangoDB再遭重创 大客户Lyft即将弃用改投亚马逊","time":"02-27 15:34","rectime":"02-27 15:55","uts":1551254141217,"feed_title":"创见","img":"https://aimg0.tuicool.com/rEjyqmJ.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"Zfiiuue","title":"除了夜游文创，博物馆还靠什么谋生？","time":"02-27 10:51","rectime":"02-27 15:37","uts":1551253038494,"feed_title":"虎嗅网","img":"https://aimg2.tuicool.com/i6FvMf6.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"MzYjEvQ","title":"微信更新版本引关注 朋友圈浏览记录或被公开成焦点","time":"02-27 10:44","rectime":"02-27 15:36","uts":1551253016461,"feed_title":"cnBeta","img":"https://aimg0.tuicool.com/UfaqEvQ.png","abs":"","cmt":0,"st":0,"go":0},{"id":"mMJBJfr","title":"商业增长：产业互联网的增长逻辑","time":"02-27 12:25","rectime":"02-27 15:36","uts":1551252994425,"feed_title":"增长黑客","img":"https://aimg1.tuicool.com/ei2Ez2A.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"uMfeuqZ","title":"从小米的主动到京东的被动，如何突破格局束缚成了当务之急","time":"02-27 13:58","rectime":"02-27 15:27","uts":1551252454985,"feed_title":"亿欧网","img":"https://aimg2.tuicool.com/2YJ732j.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"iQj6nqq","title":"最前线 | Apple Music或入驻谷歌智能音箱，为苹果打开服务版图","time":"02-27 15:16","rectime":"02-27 15:21","uts":1551252105521,"feed_title":"36氪","img":"","abs":"","cmt":0,"st":0,"go":0},{"id":"E7Bfqei","title":"到底是谁在监督马斯克在Twitter发推文？","time":"02-27 12:06","rectime":"02-27 15:05","uts":1551251101578,"feed_title":"砍柴网","img":"https://aimg2.tuicool.com/ErmABfZ.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"jyY3muV","title":"美国联邦贸易委员会成立特别工作组，严查科技垄断","time":"02-27 14:15","rectime":"02-27 15:06","uts":1551251208343,"feed_title":"猎云网","img":"","abs":"","cmt":0,"st":0,"go":0},{"id":"ueyUnyE","title":"HIV如何感染了上百万中国人","time":"02-27 08:51","rectime":"02-27 14:42","uts":1551249761008,"feed_title":"虎嗅网","img":"https://aimg0.tuicool.com/Eb63u2y.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"3iIbMvV","title":"艾媒报告：2018-2019中国有声书市场专题研究报告","time":"02-27 14:40","rectime":"02-27 14:40","uts":1551249626557,"feed_title":"互联网的一些事","img":"https://aimg1.tuicool.com/JbANZzf.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"eEnmUrv","title":"沃达丰：封堵华为将拖累欧洲5G建设进度，美国要用证据说话","time":"02-27 13:06","rectime":"02-27 14:25","uts":1551248702559,"feed_title":"腾讯科技","img":"","abs":"","cmt":0,"st":0,"go":0},{"id":"Nbiqyu6","title":"苏宁：收购万达百货不存在资本层面的考虑","time":"02-27 10:06","rectime":"02-27 14:25","uts":1551248700159,"feed_title":"砍柴网","img":"https://aimg2.tuicool.com/MfEBN3u.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"mQriUvu","title":"最前线丨饿了么、口碑将招聘5000名员工，新增80万蜂鸟骑手","time":"02-27 14:06","rectime":"02-27 14:21","uts":1551248503543,"feed_title":"36氪","img":"","abs":"","cmt":0,"st":0,"go":0},{"id":"zeY73ua","title":"县城手机游戏有多野？","time":"02-27 13:16","rectime":"02-27 14:14","uts":1551248040357,"feed_title":"非虚构写作文库","img":"https://aimg2.tuicool.com/MJFFjqM.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"RzUNBzB","title":"纳斯达克上线比特币指数 却成不了熊市猛药？","time":"02-27 11:36","rectime":"02-27 14:06","uts":1551247615813,"feed_title":"金色财经","img":"https://aimg1.tuicool.com/qyqeyuU.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"fAj2yq7","title":"腾讯推出师生互动工具：可查学生游戏时长、消费","time":"02-27 09:45","rectime":"02-27 14:06","uts":1551247611311,"feed_title":"游戏大观","img":"https://aimg2.tuicool.com/JF7ZFj2.gif","abs":"","cmt":0,"st":0,"go":0},{"id":"YZJJ3uN","title":"饿了么口碑：今年计划招聘5000名员工、新增80万蜂鸟骑手","time":"02-27 12:36","rectime":"02-27 13:06","uts":1551244003556,"feed_title":"腾讯科技","img":"","abs":"","cmt":0,"st":0,"go":0},{"id":"U3EBzyy","title":"焦点分析 | 甩卖、裁员、更换CEO，比特大陆的路线之争","time":"02-27 12:06","rectime":"02-27 12:51","uts":1551243094434,"feed_title":"36氪","img":"","abs":"","cmt":0,"st":0,"go":0},{"id":"f2Ybemy","title":"国内云计算厂商众生相：四大阵营十几家企业生存盘点","time":"02-27 08:26","rectime":"02-27 12:45","uts":1551242701067,"feed_title":"InfoQ","img":"https://aimg2.tuicool.com/EFj6NbJ.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"auEbYbQ","title":"与华纳撕破脸，Spotify\u200b印度梦碎？","time":"02-27 08:05","rectime":"02-27 12:25","uts":1551241523674,"feed_title":"猎云网","img":"","abs":"","cmt":0,"st":0,"go":0},{"id":"aAjMrma","title":"观察+ | 被巴菲特看好的苹果，是一个有\u201c圈子\u201d的苹果","time":"02-27 12:21","rectime":"02-27 12:21","uts":1551241280968,"feed_title":"36氪","img":"","abs":"","cmt":0,"st":0,"go":0},{"id":"AVNzqi2","title":"何一：去中心化交易所需要自由 而你需要判断力","time":"02-27 06:21","rectime":"02-27 12:07","uts":1551240438796,"feed_title":"金色财经","img":"https://aimg1.tuicool.com/YrUbMnr.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"jeiaAby","title":"普通消费者也能买摩根币？摩根大通CEO表示有朝一日可能","time":"02-27 07:07","rectime":"02-27 11:36","uts":1551238598141,"feed_title":"比特币资讯网","img":"https://aimg0.tuicool.com/2imEbmy.png","abs":"","cmt":0,"st":0,"go":0},{"id":"m6fQVjF","title":"华为企业BG总裁阎力大：华为云不走烧钱模式，汽车业务2021年会有不错增长","time":"02-27 07:16","rectime":"02-27 11:34","uts":1551238499438,"feed_title":"36氪","img":"https://aimg1.tuicool.com/juAfAb6.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"7VrmquA","title":"解读蘑菇街上市后首个财报：净亏损收窄盈利可期，发展天花板明显","time":"02-27 07:45","rectime":"02-27 11:22","uts":1551237737380,"feed_title":"钛媒体","img":"https://aimg2.tuicool.com/iUJBvei.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"q6B7RfA","title":"人工智能开荒记","time":"02-27 10:41","rectime":"02-27 11:22","uts":1551237727627,"feed_title":"钛媒体","img":"https://aimg0.tuicool.com/M3aqu2v.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"VVr6vyf","title":"5G基站争夺战正上演 欧洲国家难舍华为","time":"02-27 05:51","rectime":"02-27 11:22","uts":1551237727315,"feed_title":"新浪科技","img":"https://aimg0.tuicool.com/BrA7NvR.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"EbMVfuF","title":"\u201c十倍高潮\u201d背后的币安KYC隐忧","time":"02-27 06:36","rectime":"02-27 11:22","uts":1551237726604,"feed_title":"金色财经","img":"https://aimg1.tuicool.com/MjIJrar.jpg","abs":"","cmt":0,"st":0,"go":0},{"id":"VnuAF3F","title":"区块链ABS受捧 局限性仍需关注","time":"02-27 07:36","rectime":"02-27 11:21","uts":1551237696745,"feed_title":"金色财经","img":"https://aimg0.tuicool.com/iU7bQj3.jpg","abs":"","cmt":0,"st":0,"go":0}]
     * lang : 1
     * pn : 0
     * cats : {"id":101000000,"desc":"科技","seo":"业界科技,苹果公司,微软","exclude":[101040000,114000000,108000000,114010000,114020000,101050000,101070000,101050200,101050300,101050400,101050500],"include":[101000000,26]}
     * cid : 101000000
     */

    private boolean has_next;
    private int lang;
    private int pn;
    private CatsBean cats;
    private int cid;
    private List<ArticleDetailBean.ArticleBean> articles = new ArrayList<>();

    public boolean isHas_next() {
        return has_next;
    }

    public void setHas_next(boolean has_next) {
        this.has_next = has_next;
    }

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public int getPn() {
        return pn;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }

    public CatsBean getCats() {
        return cats;
    }

    public void setCats(CatsBean cats) {
        this.cats = cats;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public List<ArticleDetailBean.ArticleBean> getArticles() {
        if (articles == null) {
            return new ArrayList<>();
        }
        return articles;
    }

    public void setArticles(List<ArticleDetailBean.ArticleBean> articles) {
        this.articles = articles;
    }

    public static class CatsBean {
        /**
         * id : 101000000
         * desc : 科技
         * seo : 业界科技,苹果公司,微软
         * exclude : [101040000,114000000,108000000,114010000,114020000,101050000,101070000,101050200,101050300,101050400,101050500]
         * include : [101000000,26]
         */

        private int id;
        private String desc;
        private String seo;
        private List<Integer> exclude;
        private List<Integer> include;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDesc() {
            return desc == null ? "" : desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getSeo() {
            return seo == null ? "" : seo;
        }

        public void setSeo(String seo) {
            this.seo = seo;
        }

        public List<Integer> getExclude() {
            if (exclude == null) {
                return new ArrayList<>();
            }
            return exclude;
        }

        public void setExclude(List<Integer> exclude) {
            this.exclude = exclude;
        }

        public List<Integer> getInclude() {
            if (include == null) {
                return new ArrayList<>();
            }
            return include;
        }

        public void setInclude(List<Integer> include) {
            this.include = include;
        }
    }
}
