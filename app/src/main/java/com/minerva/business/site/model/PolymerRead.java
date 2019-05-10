package com.minerva.business.site.model;

import com.minerva.base.BaseBean;
import com.minerva.business.article.list.model.ArticleBean;

import java.util.ArrayList;
import java.util.List;

public class PolymerRead extends BaseBean {
    private boolean has_next;
    private int lang;
    private int pn;
    private long time;
    private String tip;
    private String code;
    private List<ArticleBean.ArticlesBean> articles = new ArrayList<>();

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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ArticleBean.ArticlesBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleBean.ArticlesBean> articles) {
        this.articles = articles;
    }
}
