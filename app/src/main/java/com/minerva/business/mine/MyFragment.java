package com.minerva.business.mine;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseFragment;

public class MyFragment extends BaseFragment<MyViewModel> {
    @Override
    protected MyViewModel getViewModel() {
        return new MyViewModel(getActivity());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_layout;
    }

    @Override
    protected int getVariableId() {
        return BR.myVM;
    }
}
