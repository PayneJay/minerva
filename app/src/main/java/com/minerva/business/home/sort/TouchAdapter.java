package com.minerva.business.home.sort;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.minerva.R;
import com.minerva.business.site.model.SitesBean;
import com.minerva.widget.touchHelper.ItemDragListener;
import com.minerva.widget.touchHelper.ItemTouchHelperAdapter;
import com.minerva.widget.touchHelper.ItemTouchHelperViewHolder;

import java.util.Collections;
import java.util.List;

public class TouchAdapter extends RecyclerView.Adapter<TouchAdapter.TouchViewHolder> implements ItemTouchHelperAdapter {
    private List<SitesBean.ItemsBeanX> mItems;
    private ItemDragListener mListener;

    public TouchAdapter(List<SitesBean.ItemsBeanX> items, ItemDragListener listener) {
        this.mItems = items;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public TouchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TouchViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_drag_group, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final TouchViewHolder holder, int position) {
        holder.tvName.setText(mItems.get(position).getName());
        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mListener != null) {
                        mListener.onStartDrags(holder);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //互换列表中指定位置的数据
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public class TouchViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
        TextView tvName;

        TouchViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.group_item_name);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(Color.WHITE);
        }
    }
}
