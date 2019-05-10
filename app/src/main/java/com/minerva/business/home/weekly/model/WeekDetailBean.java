package com.minerva.business.home.weekly.model;

import com.minerva.base.BaseBean;
import com.minerva.business.article.list.model.ArticleBean;

import java.util.ArrayList;
import java.util.List;

public class WeekDetailBean extends BaseBean {

    /**
     * weekly : {"id":"5ca01d846d6beb77cdcc9c63","time":1553997188230}
     * articles : [{"id":"yqUZVvy","title":"长文：行业人士讨论日益成熟的移动游戏的创新前景","time":"03-29 08:58","rectime":"03-29 08:58","uts":1553821091000,"feed_title":"游戏邦","img":"https://aimg2.tuicool.com/rQBZjqa.jpg!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"JFZRNbj","title":"EMUI 9.1 的再一次进化，多了不少让「全面屏」更好用的细节","time":"03-29 14:28","rectime":"03-29 14:28","uts":1553840894000,"feed_title":"少数派","img":"https://aimg2.tuicool.com/b6zYzaf.jpg!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"ZRJrQbQ","title":"以四个典型超休闲类手游为例探讨核心循环设计","time":"03-27 09:03","rectime":"03-27 09:03","uts":1553648631000,"feed_title":"游戏邦","img":"https://aimg1.tuicool.com/IFzE3mm.jpg!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"JrYJNva","title":"JavaScript 中的位运算和权限设计","time":"03-30 00:23","rectime":"03-30 00:23","uts":1553876597000,"feed_title":"KeenWon","img":"","abs":"","cmt":0,"st":0,"go":0},{"id":"neQVbmJ","title":"ijkplayer框架简析 -- 读取数据","time":"03-30 15:44","rectime":"03-30 15:44","uts":1553931868958,"feed_title":"yydcdut","img":"","abs":"","cmt":0,"st":0,"go":0},{"id":"BJbUJvn","title":"Go语言2018年度调查结果报告","time":"03-30 15:43","rectime":"03-30 15:43","uts":1553931787710,"feed_title":"Go语言中文网","img":"","abs":"","cmt":0,"st":0,"go":0},{"id":"EFr6Z3q","title":"Canvas和属性动画实现好看的效果","time":"03-30 18:34","rectime":"03-30 18:34","uts":1553942045000,"feed_title":"mingyunxiaohai的专栏","img":"https://aimg1.tuicool.com/a6ziauN.gif!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"BvQzMrz","title":"为什么这家 90 后创办的公司值得阿里花 1 亿美元收购？","time":"03-30 12:12","rectime":"03-30 12:12","uts":1553919132000,"feed_title":"极客公园","img":"https://aimg1.tuicool.com/q2iAnuz.png!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"YRbumq2","title":"腾讯优图李牧青： 我们的AI安防定位与战略","time":"03-30 11:35","rectime":"03-30 11:35","uts":1553916900000,"feed_title":"雷锋网","img":"https://aimg2.tuicool.com/ri2MbuJ.png!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"632UJjv","title":"阿里投资趣头条，或许只是一笔股权抵押借款","time":"03-30 06:58","rectime":"03-30 06:58","uts":1553900306968,"feed_title":"砍柴网","img":"https://aimg0.tuicool.com/RVj2iae.jpg!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"yuiaM3F","title":"抖音的尴尬VS社交的机会","time":"03-27 10:17","rectime":"03-27 10:17","uts":1553653077803,"feed_title":"i黑马","img":"https://aimg1.tuicool.com/Nfumeur.jpg!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"yqYBfyV","title":"浙大提出会打德扑的「自我博弈」AI，还会玩射击游戏","time":"03-25 12:48","rectime":"03-25 12:48","uts":1553489298000,"feed_title":"机器之心","img":"https://aimg1.tuicool.com/n6nQbqf.png!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"meiuMvY","title":"程序员想转产品经理？不妨先看看这几本书","time":"03-25 22:15","rectime":"03-25 22:15","uts":1553523316000,"feed_title":"51CTO","img":"https://aimg0.tuicool.com/faEN3iz.jpg!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"QJJjqyZ","title":"🔒 CSP\u2014\u2014前端安全第一道防线","time":"03-30 00:43","rectime":"03-30 00:43","uts":1553877788878,"feed_title":"SegmentFault","img":"https://aimg1.tuicool.com/EJJbIr2.jpg!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"BvYr6jM","title":"Xfermode实现刮刮卡","time":"03-30 18:29","rectime":"03-30 18:29","uts":1553941768000,"feed_title":"mingyunxiaohai的专栏","img":"https://aimg2.tuicool.com/Q7zU7bB.gif!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"m2iUvqN","title":"挖洞经验 | 利用跨站WebSocket劫持（CSWH）实现账户劫持","time":"03-30 13:13","rectime":"03-30 13:13","uts":1553922795366,"feed_title":"FreeBuf","img":"https://aimg1.tuicool.com/ZNfmAzv.jpg!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"7ri6Vnn","title":"文字编辑之：行高","time":"03-29 17:27","rectime":"03-29 17:27","uts":1553851673000,"feed_title":"设计达人","img":"https://aimg2.tuicool.com/6jueymj.jpg!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"Vz2qEzu","title":"一款车机系统的「自我修养」","time":"03-29 09:28","rectime":"03-29 09:28","uts":1553822882097,"feed_title":"新芽","img":"","abs":"","cmt":0,"st":0,"go":0},{"id":"JbAR7nq","title":"极客荐 | 收好这 18 款「快捷指令」，让你的 iPhone 更好用（3.0 完整版）","time":"03-29 16:39","rectime":"03-29 16:39","uts":1553848797000,"feed_title":"极客公园","img":"https://aimg2.tuicool.com/miYVf2v.jpg!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"imMFZbZ","title":"我计划收购200个公共厕所，谋求科创板上市","time":"03-29 10:14","rectime":"03-29 10:14","uts":1553825657309,"feed_title":"新浪创业","img":"https://aimg1.tuicool.com/feyAneF.jpg!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"7nqeIji","title":"在美国开发无障碍网站的联邦要求清单","time":"03-25 19:24","rectime":"03-25 19:24","uts":1553513087000,"feed_title":"银河系技术博客","img":"https://aimg2.tuicool.com/Y7b2UbJ.jpg!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"2mMryab","title":"移动互联网下半场，iOS 开发者如何\u201c高薪\u201d成长？","time":"03-28 15:41","rectime":"03-28 15:41","uts":1553758895851,"feed_title":"InfoQ","img":"https://aimg0.tuicool.com/NFjimuE.jpg!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"uiaUjqB","title":"京东购物在微信等场景下的算法应用实践","time":"03-30 06:08","rectime":"03-30 06:08","uts":1553897328249,"feed_title":"InfoQ","img":"https://aimg0.tuicool.com/uayyQrZ.jpg!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"F7fEVvi","title":"【VSC】Snippets不完全指南","time":"03-29 18:36","rectime":"03-29 18:36","uts":1553855775000,"feed_title":"大搜车产品技术设计团队","img":"https://aimg1.tuicool.com/aeayQri.png!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"yUjumyR","title":"Java 12：Teeing Collector","time":"03-30 18:42","rectime":"03-30 18:42","uts":1553942564000,"feed_title":"银河系技术博客","img":"https://aimg2.tuicool.com/6faYrmf.png!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"2umu6zQ","title":"百度 App 网络深度优化系列（二）：连接优化","time":"03-29 05:41","rectime":"03-29 05:41","uts":1553809270457,"feed_title":"InfoQ","img":"https://aimg2.tuicool.com/bueqIvE.png!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"Vbe26bN","title":"红衣教主周鸿祎的 IoT 主场：安全大脑是内核，智能音箱是野心","time":"03-30 12:13","rectime":"03-30 12:13","uts":1553919183000,"feed_title":"极客公园","img":"https://aimg1.tuicool.com/3UNzYnB.jpg!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"QF7vme2","title":"Lyft终于上市了，但它更说明网约车真的不是一个好生意","time":"03-31 09:49","rectime":"03-31 09:49","uts":1553996941671,"feed_title":"PingWest","img":"https://aimg0.tuicool.com/N7jQFrU.png!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"qma6jiE","title":"Lyft领军独角兽上市大潮 无法盈利华尔街也不在乎","time":"03-29 08:28","rectime":"03-29 08:28","uts":1553819328347,"feed_title":"艾瑞网","img":"https://aimg0.tuicool.com/N7J3Un2.jpg!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"zQbquun","title":"再也不见 Google+","time":"03-30 08:00","rectime":"03-30 08:00","uts":1553904000000,"feed_title":"Verne in GitHub","img":"https://aimg1.tuicool.com/2mY3yeE.jpg!middle","abs":"","cmt":0,"st":0,"go":0}]
     */

    private WeeklyBean weekly;
    private List<ArticleBean.ArticlesBean> articles;

    public WeeklyBean getWeekly() {
        return weekly;
    }

    public void setWeekly(WeeklyBean weekly) {
        this.weekly = weekly;
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

    public static class WeeklyBean {
        /**
         * id : 5ca01d846d6beb77cdcc9c63
         * time : 1553997188230
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
