package com.minerva.business.search;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;

public class SearchActivity extends BaseActivity<SearchViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_search_layout;
    }

    @Override
    protected SearchViewModel getViewModel() {
        return new SearchViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.searchVM;
    }
}
