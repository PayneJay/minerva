package com.minerva.business.article.detail;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.business.mine.journal.MyJournalViewModel;

public class AddToJournalViewModel extends MyJournalViewModel {
    private ISelectJournalListener mListener;
    public View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onBackClick();
            }
        }
    };

    public Toolbar.OnMenuItemClickListener goListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.add_journal) {
                if (mListener != null) {
                    mListener.onAddClick(catID);
                }
            }
            return true;
        }
    };

    AddToJournalViewModel(Context context) {
        super(context);
        requestServer();
    }

    public void setListener(ISelectJournalListener listener) {
        this.mListener = listener;
    }
}
