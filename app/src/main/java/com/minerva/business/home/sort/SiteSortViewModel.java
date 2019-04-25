package com.minerva.business.home.sort;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.home.weekly.WeeklyItemViewModel;
import com.minerva.common.BlankViewModel;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class SiteSortViewModel extends BaseViewModel {
    public OnItemBind<BaseViewModel> sortItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            if (item instanceof WeeklyItemViewModel) {
                itemBinding.set(BR.weekItemVM, R.layout.item_weekly_layout);
            }
            if (item instanceof BlankViewModel) {
                itemBinding.set(BR.vmBlank, R.layout.item_blank_layout);
            }
        }
    };
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };
    public Toolbar.OnMenuItemClickListener menuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.add_journal) {
            }
            return true;
        }
    };

    SiteSortViewModel(Context context) {
        super(context);
    }

    public ObservableList<BaseViewModel> getItems() {
        return new ObservableArrayList<>();
    }
}
