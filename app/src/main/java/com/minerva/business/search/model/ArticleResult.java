package com.minerva.business.search.model;

import com.minerva.base.BaseBean;
import com.minerva.business.article.detail.model.ArticleDetailBean;

import java.util.ArrayList;
import java.util.List;

public class ArticleResult extends BaseBean {

    /**
     * pn : 0
     * limit : 30
     * lang : 1
     * total : 101898
     * has_next : true
     */

    private int pn;
    private int limit;
    private int lang;
    private int total;
    private boolean has_next;
    private List<ArticleDetailBean.ArticleBean> articles = new ArrayList<>();

    public int getPn() {
        return pn;
    }

    public void setPn(int pn) {
        this.pn = pn;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isHas_next() {
        return has_next;
    }

    public void setHas_next(boolean has_next) {
        this.has_next = has_next;
    }

    public List<ArticleDetailBean.ArticleBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleDetailBean.ArticleBean> articles) {
        this.articles = articles;
    }
}
