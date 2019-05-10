package com.minerva.business.article.comment;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;
import com.minerva.business.article.comment.model.CommentModel;

public class CommentActivity extends BaseActivity<CommentViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_comment_layout;
    }

    @Override
    protected CommentViewModel getViewModel() {
        return new CommentViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.commentVM;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CommentModel.getInstance().onDestroy();
    }
}
