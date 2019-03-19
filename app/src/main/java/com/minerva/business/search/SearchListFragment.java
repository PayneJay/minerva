package com.minerva.business.search;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseFragment;

public class SearchListFragment extends BaseFragment<SearchListViewModel> {
    private SearchListViewModel searchListViewModel;
    private int tab; //标记当前的tab

    public void setTab(int tab) {
        this.tab = tab;
    }

    @Override
    protected SearchListViewModel getViewModel() {
        if (searchListViewModel == null) {
            searchListViewModel = new SearchListViewModel(getActivity(), tab);
        }
        return searchListViewModel;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_list_layout;
    }

    @Override
    protected int getVariableId() {
        return BR.searchListVM;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (searchListViewModel != null) {
            searchListViewModel.onDetach();
        }
    }
}
