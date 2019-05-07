package com.minerva.business.category.mag;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseFragment;
import com.minerva.business.category.mag.model.MagModel;

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        MagModel.getInstance().onDestroy();
    }
}
