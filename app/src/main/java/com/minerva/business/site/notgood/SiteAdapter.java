package com.minerva.business.site.notgood;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.hgdendi.expandablerecycleradapter.BaseExpandableRecyclerViewAdapter;
import com.minerva.R;
import com.minerva.business.site.model.SitesBean;
import com.minerva.common.Constants;

import java.util.List;

class SiteAdapter extends BaseExpandableRecyclerViewAdapter<SitesBean.ItemsBeanX, SitesBean.ItemsBeanX.ItemsBean, SiteAdapter.GroupVH, SiteAdapter.ChildVH> {
    private List<SitesBean.ItemsBeanX> mList;
    private Context context;
    RequestOptions mRequestOptions = RequestOptions.circleCropTransform()
            .diskCacheStrategy(DiskCacheStrategy.NONE)//不做磁盘缓存
            .skipMemoryCache(true);//不做内存缓存

    public SiteAdapter(Context context, List<SitesBean.ItemsBeanX> list) {
        this.context = context;
        mList = list;
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
    public void onBindGroupViewHolder(GroupVH holder, SitesBean.ItemsBeanX sampleGroupBean, boolean isExpanding) {
        holder.nameTv.setText(sampleGroupBean.getName());
        if (sampleGroupBean.isExpandable()) {
            holder.foldIv.setVisibility(View.VISIBLE);
            holder.foldIv.setImageResource(isExpanding ? R.drawable.ic_arrow_expanding : R.drawable.ic_arrow_folding);
        } else {
            holder.foldIv.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBindChildViewHolder(final ChildVH holder, SitesBean.ItemsBeanX groupBean, SitesBean.ItemsBeanX.ItemsBean sampleChildBean) {
        holder.nameTv.setText(sampleChildBean.getName());
        holder.nameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constants.showToast(context);
            }
        });

        Glide.with(context)
                .load(sampleChildBean.getImage())
                .placeholder(R.drawable.icon_topic_default)
                .error(R.drawable.icon_topic_default)
                .apply(mRequestOptions)
                .into(holder.icon);
    }

    static class GroupVH extends BaseExpandableRecyclerViewAdapter.BaseGroupViewHolder {
        ImageView foldIv;
        TextView nameTv;

        GroupVH(View itemView) {
            super(itemView);
            foldIv = itemView.findViewById(R.id.group_item_indicator);
            nameTv = itemView.findViewById(R.id.group_item_name);
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
