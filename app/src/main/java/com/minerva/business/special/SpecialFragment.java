package com.minerva.business.special;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseFragment;
import com.minerva.business.special.column.SpecialViewModel;

public class SpecialFragment extends BaseFragment<SpecialViewModel> {
    @Override
    protected SpecialViewModel getViewModel() {
        return new SpecialViewModel(getActivity());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_special_layout;
    }

    @Override
    protected int getVariableId() {
        return BR.specialVM;
    }
}
