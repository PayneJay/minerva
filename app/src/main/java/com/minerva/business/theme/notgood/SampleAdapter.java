package com.minerva.business.theme.notgood;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hgdendi.expandablerecycleradapter.BaseExpandableRecyclerViewAdapter;
import com.minerva.R;
import com.minerva.business.article.detail.ArticleDetailActivity;

import java.util.List;

class SampleAdapter extends BaseExpandableRecyclerViewAdapter<SampleGroupBean, SampleChildBean, SampleAdapter.GroupVH, SampleAdapter.ChildVH> {
    private List<SampleGroupBean> mList;
    private Context context;

    public SampleAdapter(Context context, List<SampleGroupBean> list) {
        this.context = context;
        mList = list;
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public SampleGroupBean getGroupItem(int position) {
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
    public void onBindGroupViewHolder(GroupVH holder, SampleGroupBean sampleGroupBean, boolean isExpanding) {
        holder.nameTv.setText(sampleGroupBean.getName());
        if (sampleGroupBean.isExpandable()) {
            holder.foldIv.setVisibility(View.VISIBLE);
            holder.foldIv.setImageResource(isExpanding ? R.drawable.ic_arrow_expanding : R.drawable.ic_arrow_folding);
        } else {
            holder.foldIv.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBindChildViewHolder(ChildVH holder, SampleGroupBean groupBean, SampleChildBean sampleChildBean) {
        holder.nameTv.setText(sampleChildBean.getName());
        holder.nameTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ArticleDetailActivity.class));
            }
        });
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

        ChildVH(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.child_item_name);
        }
    }
}
