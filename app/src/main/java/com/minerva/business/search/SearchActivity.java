package com.minerva.business.search;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;

public class SearchActivity extends BaseActivity<SearchViewModel> {
    private SearchViewModel searchViewModel;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_search_layout;
    }

    @Override
    protected SearchViewModel getViewModel() {
        if (searchViewModel == null) {
            searchViewModel = new SearchViewModel(this);
        }
        return searchViewModel;
    }

    @Override
    protected int getVariableID() {
        return BR.searchVM;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (searchViewModel != null) {
            searchViewModel.onDetach();
        }
    }
}
