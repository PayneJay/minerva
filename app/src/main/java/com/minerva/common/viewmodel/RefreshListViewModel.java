package com.minerva.common.viewmodel;

import android.content.Context;
import android.databinding.ObservableBoolean;

import com.minerva.R;
import com.minerva.base.BaseViewModel;

public class RefreshListViewModel extends BaseViewModel {
    public ObservableBoolean refreshing = new ObservableBoolean();

    public RefreshListViewModel(Context context) {
        super(context);
    }

    /**
     * 配置下拉刷新颜色
     *
     * @return
     */
    public int[] getColors() {
        return new int[]{R.color.colorPrimary, R.color.color_1E90FF, R.color.color_FF77FF};
    }

    /**
     * 请求服务器
     */
    protected void requestServer() {
    }

    /**
     * 创建ViewModel
     */
    protected void createViewModel() {
    }
}
