package com.minerva.business.mine.collection;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;

public class MyCollectionActivity extends BaseActivity<CollectionViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_my_collection_layout;
    }

    @Override
    protected CollectionViewModel getViewModel() {
        return new CollectionViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.collectionVM;
    }
}
