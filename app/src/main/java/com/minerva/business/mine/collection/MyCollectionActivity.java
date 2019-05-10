package com.minerva.business.mine.collection;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;

public class MyCollectionActivity extends BaseActivity<CollectionViewModel> {
    private CollectionViewModel collectionViewModel;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_my_collection_layout;
    }

    @Override
    protected CollectionViewModel getViewModel() {
        if (collectionViewModel == null) {
            collectionViewModel = new CollectionViewModel(this);
        }
        return collectionViewModel;
    }

    @Override
    protected int getVariableID() {
        return BR.collectionVM;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (collectionViewModel != null) {
            collectionViewModel.onDetach();
        }
    }
}
