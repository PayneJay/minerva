package com.minerva.common.imagebrowse;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;

public class ImageBrowseActivity extends BaseActivity<ImageBrowseViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_image_browse_layout;
    }

    @Override
    protected ImageBrowseViewModel getViewModel() {
        return new ImageBrowseViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.imgBrowseVM;
    }
}
