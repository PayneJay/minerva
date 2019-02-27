package com.minerva.theme;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseFragment;

public class ThemeFragment extends BaseFragment<ThemeViewModel> {

    @Override
    protected ThemeViewModel getViewModel() {
        return new ThemeViewModel(getActivity());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_theme_layout;
    }

    @Override
    protected int getVariableId() {
        return BR.themeVM;
    }
}
