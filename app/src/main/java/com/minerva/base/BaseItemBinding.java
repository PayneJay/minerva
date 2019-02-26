package com.minerva.base;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

/**
 * ================================================
 * description:
 * ================================================
 * package_name: com.waka.mvvm.base
 * author: PayneJay
 * date: 2018/6/8.
 */

public class BaseItemBinding implements OnItemBind<BaseViewModel> {
    public static BaseItemBinding INSTANCE;

    public static BaseItemBinding get() {
        if (INSTANCE == null) {
            INSTANCE = new BaseItemBinding();
        }
        return INSTANCE;
    }

    @Override
    public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
        item.setItemPosition(position);
    }
}
