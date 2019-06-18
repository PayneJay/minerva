package com.minerva.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class Article {
    @Id(autoincrement = true)
    private long id;

    private String aid;
    private String title;
    private String time;
    private String feed_title;
    private String img;
    private int type;//0：待读列表，1：阅读历史

    @Generated(hash = 1025068939)
    public Article(String aid, String title, String time, String feed_title,
                   String img, int type) {
        this.aid = aid;
        this.title = title;
        this.time = time;
        this.feed_title = feed_title;
        this.img = img;
        this.type = type;
    }

    @Generated(hash = 742516792)
    public Article() {
    }

    public String getAid() {
        return this.aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFeed_title() {
        return this.feed_title;
    }

    public void setFeed_title(String feed_title) {
        this.feed_title = feed_title;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
