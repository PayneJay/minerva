package com.minerva.business.site.imperfect;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.hgdendi.expandablerecycleradapter.BaseExpandableRecyclerViewAdapter;
import com.minerva.R;
import com.minerva.business.site.PolymerReadActivity;
import com.minerva.business.site.detail.PeriodicalDetailActivity;
import com.minerva.business.site.model.SitesBean;
import com.minerva.common.Constants;

import java.util.List;

class SiteAdapter extends BaseExpandableRecyclerViewAdapter<SitesBean.ItemsBeanX, SitesBean.ItemsBeanX.ItemsBean, SiteAdapter.GroupVH, SiteAdapter.ChildVH> {
    private List<SitesBean.ItemsBeanX> mList;
    private Context context;
    private ItemLongClickListener listener;

    SiteAdapter(Context context, List<SitesBean.ItemsBeanX> list, ItemLongClickListener listener) {
        this.context = context;
        this.listener = listener;
        mList = list;
    }

    public void setList(List<SitesBean.ItemsBeanX> mList) {
        this.mList = mList;
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public SitesBean.ItemsBeanX getGroupItem(int position) {
        return mList.get(position);
    }

    @Override
    public GroupVH onCreateGroupViewHolder(ViewGroup parent, int groupViewType) {
        return new GroupVH(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_group, parent, false));
    }

    @Override
    public ChildVH onCreateChildViewHolder(ViewGroup parent, int childViewType) {
        return new ChildVH(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.listitem_child, parent, false));
    }

    @Override
    public void onBindGroupViewHolder(GroupVH holder, final SitesBean.ItemsBeanX groupBean, boolean isExpanding) {
        holder.nameTv.setText(groupBean.getName());
        if (groupBean.isExpandable()) {
            holder.foldIv.setVisibility(View.VISIBLE);
            holder.foldIv.setImageResource(isExpanding ? R.drawable.ic_arrow_expanding : R.drawable.ic_arrow_folding);
        } else {
            holder.foldIv.setVisibility(View.VISIBLE);
            holder.foldIv.setImageResource(R.drawable.ic_arrow_expanding);
        }
        holder.readTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PolymerReadActivity.class);
                intent.putExtra(Constants.KeyExtra.POLYMER_ID, groupBean.getId());
                intent.putExtra(Constants.KeyExtra.PERIODICAL_NAME, groupBean.getName());
                context.startActivity(intent);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onGroupLongClick(groupBean.getId(), groupBean.getName());
                }
                return true;
            }
        });
    }

    @Override
    public void onBindChildViewHolder(final ChildVH holder, final SitesBean.ItemsBeanX groupBean, final SitesBean.ItemsBeanX.ItemsBean childBean) {
        holder.nameTv.setText(childBean.getName());
        holder.nameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PeriodicalDetailActivity.class);
                intent.putExtra(Constants.KeyExtra.PERIODICAL_ID, childBean.getId());
                intent.putExtra(Constants.KeyExtra.PERIODICAL_IMAGE, childBean.getImage());
                intent.putExtra(Constants.KeyExtra.PERIODICAL_NAME, childBean.getName());
                context.startActivity(intent);
            }
        });

        Glide.with(context)
                .load(childBean.getImage())
                .placeholder(R.drawable.icon_topic_default)
                .error(R.drawable.icon_topic_default)
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(holder.icon);

        holder.nameTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (listener != null) {
                    listener.onChildLongClick(groupBean.getId(), childBean.getId());
                }
                return true;
            }
        });
    }

    static class GroupVH extends BaseExpandableRecyclerViewAdapter.BaseGroupViewHolder {
        ImageView foldIv;
        TextView nameTv, readTv;

        GroupVH(View itemView) {
            super(itemView);
            foldIv = itemView.findViewById(R.id.group_item_indicator);
            nameTv = itemView.findViewById(R.id.group_item_name);
            readTv = itemView.findViewById(R.id.group_item_aggregate_reading);
        }

        @Override
        protected void onExpandStatusChanged(RecyclerView.Adapter relatedAdapter, boolean isExpanding) {
            foldIv.setImageResource(isExpanding ? R.drawable.ic_arrow_expanding : R.drawable.ic_arrow_folding);
        }
    }

    static class ChildVH extends RecyclerView.ViewHolder {
        TextView nameTv;
        ImageView icon;

        ChildVH(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.child_item_name);
            icon = itemView.findViewById(R.id.img_child_icon);
        }
    }

}
