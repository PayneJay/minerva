package com.minerva.business.article.comment;

import android.content.Context;
import android.databinding.ObservableField;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.minerva.R;
import com.minerva.base.BaseViewModel;

public class CommentReportViewModel extends BaseViewModel {
    public ObservableField<String> commentContent = new ObservableField<>();
    public View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onBackClick();
            }
        }
    };

    public Toolbar.OnMenuItemClickListener goListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.add_journal) {
                if (listener != null) {
                    listener.onCommentSubmit(commentContent.get());
                }
            }
            return true;
        }
    };
    private ICommentListener listener;

    CommentReportViewModel(Context context, ICommentListener listener) {
        super(context);
        this.listener = listener;
    }
}
