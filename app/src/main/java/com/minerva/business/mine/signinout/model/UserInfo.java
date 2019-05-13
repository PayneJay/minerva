package com.minerva.business.mine.signinout.model;

import com.minerva.base.BaseBean;

public class UserInfo extends BaseBean {

    /**
     * user : {"id":2350265696,"email":"pjwei1013@163.com","name":"Minerva","ctime":1552447679,"uid":"2350265696","profile":"https://static0.tuicool.com/images/profile/default_profile_7.jpg","token":"23b4d0496b4dd41fd0ad7a01dfcb7b43","notification_num":0,"weibo_id":-1,"weibo_name":"","qq_id":"","qq_name":"","weixin_name":"","flyme_name":""}
     * auditing_version : 1.0.1
     */

    private UserBean user;
    private String auditing_version;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getAuditing_version() {
        return auditing_version;
    }

    public void setAuditing_version(String auditing_version) {
        this.auditing_version = auditing_version;
    }

    public static class UserBean {
        /**
         * id : 2350265696
         * email : pjwei1013@163.com
         * name : Minerva
         * ctime : 1552447679
         * uid : 2350265696
         * profile : https://static0.tuicool.com/images/profile/default_profile_7.jpg
         * token : 23b4d0496b4dd41fd0ad7a01dfcb7b43
         * notification_num : 0
         * weibo_id : -1
         * weibo_name :
         * qq_id :
         * qq_name :
         * weixin_name :
         * flyme_name :
         */

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

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCtime() {
            return ctime;
        }

        public void setCtime(int ctime) {
            this.ctime = ctime;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getNotification_num() {
            return notification_num;
        }

        public void setNotification_num(int notification_num) {
            this.notification_num = notification_num;
        }

        public int getWeibo_id() {
            return weibo_id;
        }

        public void setWeibo_id(int weibo_id) {
            this.weibo_id = weibo_id;
        }

        public String getWeibo_name() {
            return weibo_name;
        }

        public void setWeibo_name(String weibo_name) {
            this.weibo_name = weibo_name;
        }

        public String getQq_id() {
            return qq_id;
        }

        public void setQq_id(String qq_id) {
            this.qq_id = qq_id;
        }

        public String getQq_name() {
            return qq_name;
        }

        public void setQq_name(String qq_name) {
            this.qq_name = qq_name;
        }

        public String getWeixin_name() {
            return weixin_name;
        }

        public void setWeixin_name(String weixin_name) {
            this.weixin_name = weixin_name;
        }

        public String getFlyme_name() {
            return flyme_name;
        }

        public void setFlyme_name(String flyme_name) {
            this.flyme_name = flyme_name;
        }
    }
}
