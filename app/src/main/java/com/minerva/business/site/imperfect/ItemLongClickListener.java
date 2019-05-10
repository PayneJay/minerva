package com.minerva.business.site.imperfect;

public interface ItemLongClickListener {
    void onGroupLongClick(int groupId, String name);

    void onChildLongClick(int groupId, String child);
}
