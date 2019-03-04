package com.minerva.business.search;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseFragment;

public class SearchListFragment extends BaseFragment<SearchListViewModel> {
    @Override
    protected SearchListViewModel getViewModel() {
        return new SearchListViewModel(getActivity());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_list_layout;
    }

    @Override
    protected int getVariableId() {
        return BR.searchListVM;
    }
}
