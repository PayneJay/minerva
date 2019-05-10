package com.minerva.business.article.comment.model;

import com.minerva.base.BaseBean;

import java.util.ArrayList;
import java.util.List;

public class CommentListBean extends BaseBean {

    /**
     * cnt : 1
     * comments : [{"id":32984,"content":"added DCCC","uid":2350265696,"username":"Minerva","avatar":"https://static0.tuicool.com/profile/2350265696_1553337194.png","uts":1556536819000,"timestamp":"04-29 19:20","editable":true,"st":1,"type":0}]
     * has_next : false
     */

    private int cnt;
    private boolean has_next;
    private List<CommentDetail.CommentBean> comments;

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public boolean isHas_next() {
        return has_next;
    }

    public void setHas_next(boolean has_next) {
        this.has_next = has_next;
    }

    public List<CommentDetail.CommentBean> getComments() {
        if (comments == null) {
            return new ArrayList<>();
        }
        return comments;
    }

    public void setComments(List<CommentDetail.CommentBean> comments) {
        this.comments = comments;
    }
}
