package com.minerva.business.mine.journal.kan;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;

public class FavKansActivity extends BaseActivity<FavKanViewModel> {
    private FavKanViewModel kanViewModel;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_fav_kan_layout;
    }

    @Override
    protected FavKanViewModel getViewModel() {
        if (kanViewModel == null) {
            kanViewModel = new FavKanViewModel(this, getClass().getSimpleName());
        }
        return kanViewModel;
    }

    @Override
    protected int getVariableID() {
        return BR.favKanVM;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (kanViewModel != null) {
            kanViewModel.onDetach();
        }
    }
}
