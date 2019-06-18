package com.minerva.business.mine.signinout.model;

import com.minerva.base.BaseBean;
import com.minerva.db.User;

public class UserInfo extends BaseBean {

    /**
     * user : {"id":2350265696,"email":"pjwei1013@163.com","name":"Minerva","ctime":1552447679,"uid":"2350265696","profile":"https://static0.tuicool.com/images/profile/default_profile_7.jpg","token":"23b4d0496b4dd41fd0ad7a01dfcb7b43","notification_num":0,"weibo_id":-1,"weibo_name":"","qq_id":"","qq_name":"","weixin_name":"","flyme_name":""}
     * auditing_version : 1.0.1
     */

    private User user;
    private String auditing_version;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAuditing_version() {
        return auditing_version;
    }

    public void setAuditing_version(String auditing_version) {
        this.auditing_version = auditing_version;
    }
}
