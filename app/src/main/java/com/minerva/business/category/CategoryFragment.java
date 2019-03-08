package com.minerva.business.category;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseFragment;

public class CategoryFragment extends BaseFragment<CategoryViewModel> {
    private CategoryViewModel categoryViewModel;

    @Override
    protected CategoryViewModel getViewModel() {
        if (categoryViewModel == null) {
            categoryViewModel = new CategoryViewModel(getActivity());
        }
        categoryViewModel.fragment.set(this);
        return categoryViewModel;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_category_layout;
    }

    @Override
    protected int getVariableId() {
        return BR.categoryVM;
    }
}
