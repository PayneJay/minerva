package com.minerva.business.article.list.model;

public class ArticleType {
    private String tabTitle;
    private String tabId;

    public ArticleType(String tabTitle, String tabId) {
        this.tabTitle = tabTitle;
        this.tabId = tabId;
    }

    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public String getTabId() {
        return tabId;
    }

    public void setTabId(String tabId) {
        this.tabId = tabId;
    }
}
