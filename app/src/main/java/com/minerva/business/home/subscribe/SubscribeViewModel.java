package com.minerva.business.home.subscribe;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;

public class SubscribeViewModel extends BaseViewModel {
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };

    public Toolbar.OnMenuItemClickListener menuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.my_journal) {
            }
            return true;
        }
    };

    SubscribeViewModel(Context context) {
        super(context);
    }
}
