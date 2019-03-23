package com.minerva.business.mine.collection;

import android.content.Context;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.category.mag.PeriodViewModel;
import com.minerva.common.Constants;
import com.minerva.utils.ResouceUtils;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class CollectionViewModel extends PeriodViewModel {
    private String articleID;
    public OnItemBind<BaseViewModel> collectionItemBind = new OnItemBind<BaseViewModel>() {
        @Override
        public void onItemBind(ItemBinding itemBinding, int position, BaseViewModel item) {
            switch (item.getViewType()) {
                case Constants.RecyclerItemType.ARTICLE_COMMON_TYPE:
                    itemBinding.set(BR.articleItemVM, R.layout.item_article_common_layout);
                    break;
                case Constants.RecyclerItemType.BLANK_TYPE:
                    itemBinding.set(BR.vmBlank, R.layout.item_blank_layout);
                    break;
            }
        }
    };

    CollectionViewModel(Context context) {
        super(context, context.getClass().getSimpleName());
        mTitle = ResouceUtils.getString(R.string.mine_collection);
        articleID = ((BaseActivity) context).getIntent().getStringExtra(Constants.KeyExtra.ARTICLE_ID);
    }

}
