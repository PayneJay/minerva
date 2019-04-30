package com.minerva.business.site.detail;

import android.content.Context;
import android.databinding.ObservableField;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;
import com.minerva.common.GlobalData;
import com.minerva.utils.ResourceUtils;

public class PeriodicalTitleViewModel extends BaseViewModel {
    public ObservableField<String> imgUrl = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> subscribe = new ObservableField<>(ResourceUtils.getString(R.string.periodical_subscribe));

    PeriodicalTitleViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.PERIODICAL_TITLE_TYPE);
    }

    public void onSubscribeClick() {
        if (!GlobalData.getInstance().isLogin()) {
            Toast.makeText(context, ResourceUtils.getString(R.string.toast_please_login_first), Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
