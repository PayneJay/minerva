package com.minerva.business.mine;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseFragment;

public class MyFragment extends BaseFragment<MyViewModel> {
    private MyViewModel myViewModel;

    @Override
    protected MyViewModel getViewModel() {
        if (myViewModel == null) {
            myViewModel = new MyViewModel(getActivity());
        }
        return myViewModel;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_my_layout;
    }

    @Override
    protected int getVariableId() {
        return BR.myVM;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myViewModel != null) {
            myViewModel.onDetach();
        }
    }
}
