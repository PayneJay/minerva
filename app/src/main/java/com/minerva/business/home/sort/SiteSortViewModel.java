package com.minerva.business.home.sort;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;

import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.site.model.SiteModel;
import com.minerva.business.site.model.SitesBean;
import com.minerva.common.MinervaLinearLayoutManager;
import com.minerva.widget.touchHelper.ItemDragListener;
import com.minerva.widget.touchHelper.ItemTouchHelperCallback;

import java.util.List;

public class SiteSortViewModel extends BaseViewModel {
    private static ItemTouchHelper itemTouchHelper;
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };
    public Toolbar.OnMenuItemClickListener menuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            return true;
        }
    };
    public ItemDragListener itemDragListener = new ItemDragListener() {
        @Override
        public void onStartDrags(RecyclerView.ViewHolder viewHolder) {
            if (itemTouchHelper != null) {
                itemTouchHelper.startDrag(viewHolder);
            }
        }
    };

    SiteSortViewModel(Context context) {
        super(context);
    }

    public List<SitesBean.ItemsBeanX> getItems() {
        return SiteModel.getInstance().getItemList();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        itemTouchHelper = null;
    }

    @BindingAdapter({"itemDragListener"})
    public static void setItemDragListener(RecyclerView recyclerView, ItemDragListener listener) {
        TouchAdapter adapter = new TouchAdapter(SiteModel.getInstance().getItemList(), listener);
        recyclerView.setLayoutManager(new MinervaLinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
        ItemTouchHelperCallback itemTouchHelperCallback = new ItemTouchHelperCallback(adapter);
        itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
}
