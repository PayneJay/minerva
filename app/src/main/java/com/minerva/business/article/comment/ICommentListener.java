package com.minerva.business.article.comment;

public interface ICommentListener {
    void onDelete(String id);

    void onBackClick();

    void onCommentSubmit(String comment);
}
