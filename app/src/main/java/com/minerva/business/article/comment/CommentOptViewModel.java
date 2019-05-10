package com.minerva.business.article.comment;

import android.content.Context;

import com.minerva.base.BaseViewModel;

public class CommentOptViewModel extends BaseViewModel {
    private ICommentOperateListener listener;

    CommentOptViewModel(Context context, ICommentOperateListener listener) {
        super(context);
        this.listener = listener;
    }

    public void onCopyClick() {
        if (listener != null) {
            listener.onCopyClick();
        }
    }

    public void onDeleteClick() {
        if (listener != null) {
            listener.onDeleteClick();
        }
    }

    public interface ICommentOperateListener {
        void onCopyClick();

        void onDeleteClick();
    }
}
