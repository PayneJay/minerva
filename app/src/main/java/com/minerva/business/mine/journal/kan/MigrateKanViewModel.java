package com.minerva.business.mine.journal.kan;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.text.TextUtils;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.collection.model.KanBean;
import com.minerva.business.mine.journal.model.JournalModel;
import com.minerva.business.site.menu.IPopupMenuItemClickListener;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class MigrateKanViewModel extends BaseViewModel {
    public ObservableList<BaseViewModel> items = new ObservableArrayList<>();
    public OnItemBind<BaseViewModel> kanItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            if (item instanceof KanItemViewModel) {
                itemBinding.set(BR.kanItemVM, R.layout.item_kan_name_layout);
            }
        }
    };

    MigrateKanViewModel(Context context) {
        super(context);
    }

    void createItemViewModel(String kanId, IKanOperateListener listener) {
        List<KanBean.ItemsBean> kanList = JournalModel.getInstance().getKanList();
        items.clear();
        for (KanBean.ItemsBean item : kanList) {
            if (TextUtils.equals(kanId, item.getId())) {
                continue;
            }
            KanItemViewModel itemViewModel = new KanItemViewModel(context, listener);
            itemViewModel.id = kanId;
            itemViewModel.targetId = item.getId();
            itemViewModel.kanName.set(item.getName());
            items.add(itemViewModel);
        }
    }
}
