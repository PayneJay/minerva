package com.minerva.business.site.detail;

import android.content.Context;
import android.databinding.ObservableField;

import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;
import com.minerva.utils.ResouceUtils;

public class PeriodicalTitleViewModel extends BaseViewModel {
    public ObservableField<String> imgUrl = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> subscribe = new ObservableField<>(ResouceUtils.getString(R.string.periodical_subscribe));

    public PeriodicalTitleViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.PERIODICAL_TYPE);
    }

    public void onSubscribeClick() {
        Constants.showToast(context);
    }
}
