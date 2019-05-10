package com.minerva.business.search;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;

import com.minerva.base.BaseViewModel;
import com.minerva.business.site.detail.PeriodicalDetailActivity;
import com.minerva.common.Constants;

public class ResultSiteViewModel extends BaseViewModel {
    public ObservableField<String> imgUrl = new ObservableField<>();
    public ObservableField<String> name = new ObservableField<>();
    public String id;

    ResultSiteViewModel(Context context) {
        super(context);
        setViewType(Constants.RecyclerItemType.SEARCH_SITE_TYPE);
    }

    public void onItemClick() {
        Intent intent = new Intent(context, PeriodicalDetailActivity.class);
        intent.putExtra(Constants.KeyExtra.PERIODICAL_ID, id);
        intent.putExtra(Constants.KeyExtra.PERIODICAL_IMAGE, imgUrl.get());
        intent.putExtra(Constants.KeyExtra.PERIODICAL_NAME, name.get());
        context.startActivity(intent);
    }

    public void onFollowClick() {
        Constants.showToast(context);
    }
}
