package com.minerva.business.mine.journal.kan.model;

import com.minerva.base.BaseBean;
import com.minerva.business.article.detail.model.ArticleDetailBean;
import com.minerva.business.mine.collection.model.KanBean;

import java.util.ArrayList;
import java.util.List;

public class FavKanBean extends BaseBean {

    /**
     * kan : {"id":731758025,"ut":1557760247000,"name":"我喜欢的","n":"我喜欢的","img":"http://img0.tuicool.com/6rUJRrY.jpg!kan","desc":"描述♞鹏杰😏是大哥，是宝藏男孩","type":0,"ac":2}
     * articles : [{"id":"qe2iaaY","title":"\u201c互联网女皇\u201d 玛丽·米克尔旗下新基金融资 12.5 亿美元","time":"04-25 15:59","rectime":"04-25 16:24","uts":1556180662000,"feed_title":"TechCrunch中国","img":"https://aimg1.tuicool.com/6rUJRrY.jpg!middle","abs":"","cmt":0,"st":0,"go":0},{"id":"nMRvIrM","title":"瑞幸没有被冤枉","time":"04-25 10:18","rectime":"04-25 16:10","uts":1556179810000,"feed_title":"钛媒体","img":"https://aimg1.tuicool.com/YfeeYnM.jpg!middle","abs":"","cmt":0,"st":0,"go":0}]
     * has_next : false
     */

    private KanBean.ItemsBean kan;
    private boolean has_next;
    private List<ArticleDetailBean.ArticleBean> articles;

    public KanBean.ItemsBean getKan() {
        return kan;
    }

    public void setKan(KanBean.ItemsBean kan) {
        this.kan = kan;
    }

    public boolean isHas_next() {
        return has_next;
    }

    public void setHas_next(boolean has_next) {
        this.has_next = has_next;
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
}
