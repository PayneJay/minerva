package com.minerva.business.home.sort;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.business.site.menu.model.MenuModel;
import com.minerva.business.site.model.SiteModel;
import com.minerva.business.site.model.SitesBean;
import com.minerva.common.MinervaLinearLayoutManager;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.ToastUtil;
import com.minerva.widget.Loading;
import com.minerva.widget.touchHelper.ItemDragListener;
import com.minerva.widget.touchHelper.ItemTouchHelperCallback;

import org.greenrobot.eventbus.EventBus;

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
            if (item.getItemId() == R.id.add_journal) {
                submitSort();
            }
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
    private Loading loading;

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

    private void submitSort() {
        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }

        MenuModel.getInstance().sortGroups(SiteModel.getInstance().getGroupIds(), new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                loading.dismiss();
                EventBus.getDefault().postSticky(baseBean);
                ((BaseActivity) context).finish();
            }

            @Override
            public void onFailure(String msg) {
                loading.dismiss();
                ToastUtil.showMsg(msg);
            }
        });

    }
}
