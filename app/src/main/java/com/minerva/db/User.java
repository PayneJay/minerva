package com.minerva.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    @Id(autoincrement = true)
    private long id;

    private String email;
    private String name;
    private int ctime;
    private String uid;
    private String profile;
    private String token;
    private int notification_num;
    private int weibo_id;
    private String weibo_name;
    private String qq_id;
    private String qq_name;
    private String weixin_name;
    private String flyme_name;
    private int oauth_type;

    @Generated(hash = 1696957688)
    public User(long id, String email, String name, int ctime, String uid,
                String profile, String token, int notification_num, int weibo_id,
                String weibo_name, String qq_id, String qq_name, String weixin_name,
                String flyme_name, int oauth_type) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.ctime = ctime;
        this.uid = uid;
        this.profile = profile;
        this.token = token;
        this.notification_num = notification_num;
        this.weibo_id = weibo_id;
        this.weibo_name = weibo_name;
        this.qq_id = qq_id;
        this.qq_name = qq_name;
        this.weixin_name = weixin_name;
        this.flyme_name = flyme_name;
        this.oauth_type = oauth_type;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCtime() {
        return this.ctime;
    }

    public void setCtime(int ctime) {
        this.ctime = ctime;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfile() {
        return this.profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getNotification_num() {
        return this.notification_num;
    }

    public void setNotification_num(int notification_num) {
        this.notification_num = notification_num;
    }

    public int getWeibo_id() {
        return this.weibo_id;
    }

    public void setWeibo_id(int weibo_id) {
        this.weibo_id = weibo_id;
    }

    public String getWeibo_name() {
        return this.weibo_name;
    }

    public void setWeibo_name(String weibo_name) {
        this.weibo_name = weibo_name;
    }

    public String getQq_id() {
        return this.qq_id;
    }

    public void setQq_id(String qq_id) {
        this.qq_id = qq_id;
    }

    public String getQq_name() {
        return this.qq_name;
    }

    public void setQq_name(String qq_name) {
        this.qq_name = qq_name;
    }

    public String getWeixin_name() {
        return this.weixin_name;
    }

    public void setWeixin_name(String weixin_name) {
        this.weixin_name = weixin_name;
    }

    public String getFlyme_name() {
        return this.flyme_name;
    }

    public void setFlyme_name(String flyme_name) {
        this.flyme_name = flyme_name;
    }

    public int getOauth_type() {
        return this.oauth_type;
    }

    public void setOauth_type(int oauth_type) {
        this.oauth_type = oauth_type;
    }
}
