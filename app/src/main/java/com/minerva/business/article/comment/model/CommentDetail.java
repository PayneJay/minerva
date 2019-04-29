package com.minerva.business.article.comment.model;

import com.minerva.base.BaseBean;

public class CommentDetail extends BaseBean {

    /**
     * comment : {"id":32984,"content":"added DCCC","uid":2350265696,"username":"Minerva","avatar":"https://static0.tuicool.com/profile/2350265696_1553337194.png","uts":1556536819000,"timestamp":"04-29 19:20","editable":true,"st":1,"type":0}
     */

    private CommentBean comment;

    public CommentBean getComment() {
        return comment;
    }

    public void setComment(CommentBean comment) {
        this.comment = comment;
    }

    public static class CommentBean {
        /**
         * id : 32984
         * content : added DCCC
         * uid : 2350265696
         * username : Minerva
         * avatar : https://static0.tuicool.com/profile/2350265696_1553337194.png
         * uts : 1556536819000
         * timestamp : 04-29 19:20
         * editable : true
         * st : 1
         * type : 0
         */

        private String id;
        private String content;
        private long uid;
        private String username;
        private String avatar;
        private long uts;
        private String timestamp;
        private boolean editable;
        private int st;
        private int type;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public long getUid() {
            return uid;
        }

        public void setUid(long uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public long getUts() {
            return uts;
        }

        public void setUts(long uts) {
            this.uts = uts;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        public boolean isEditable() {
            return editable;
        }

        public void setEditable(boolean editable) {
            this.editable = editable;
        }

        public int getSt() {
            return st;
        }

        public void setSt(int st) {
            this.st = st;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
