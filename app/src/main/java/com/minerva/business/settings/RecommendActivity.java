package com.minerva.business.settings;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;
import com.minerva.business.settings.model.SettingModel;

public class RecommendActivity extends BaseActivity<RecommendSettingViewModel> {
    private RecommendSettingViewModel viewModel;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_recommend_setting_layout;
    }

    @Override
    protected RecommendSettingViewModel getViewModel() {
        if (viewModel == null) {
            viewModel = new RecommendSettingViewModel(this);
        }
        return viewModel;
    }

    @Override
    protected int getVariableID() {
        return BR.recSettingVM;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (viewModel != null) {
            viewModel.onDetach();
        }
        SettingModel.getInstance().onDestroy();
    }
}
