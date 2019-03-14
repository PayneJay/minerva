package com.minerva.business.mine;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;

import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.about.AboutActivity;
import com.minerva.business.mine.login.LoginActivity;
import com.minerva.business.mine.user.UserEditActivity;
import com.minerva.common.Constants;
import com.minerva.common.GlobalData;
import com.minerva.utils.ResouceUtils;
import com.minerva.utils.SPUtils;

import static com.minerva.common.Constants.showToast;

public class MyViewModel extends BaseViewModel {
    public ObservableField<String> userName = new ObservableField<>(ResouceUtils.getString(R.string.mine_click_login));
    public ObservableField<String> headUrl = new ObservableField<>();

    MyViewModel(Context context) {
        super(context);
        if (GlobalData.getInstance().isLogin()) {
            headUrl.set((String) SPUtils.get(context, Constants.UserInfoKey.USER_PROFILE, ""));
            userName.set((String) SPUtils.get(context, Constants.UserInfoKey.USER_NAME, "——"));
        }
    }

    public void goLogin() {
        if (GlobalData.getInstance().isLogin()) {
            context.startActivity(new Intent(context, UserEditActivity.class));
            return;
        }
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public void goUnRead() {
        showToast(context);
    }

    public void goCollection() {
        showToast(context);
    }

    public void goJournal() {
        showToast(context);
    }

    public void goNotification() {
        showToast(context);
    }

    public void goHistory() {
        showToast(context);
    }

    public void switchModel() {
        showToast(context);
    }

    public void readOffline() {
        showToast(context);
    }

    public void checkUpgrade() {
        showToast(context);
    }

    public void goFeedback() {
        showToast(context);
    }

    public void aboutUs() {
        context.startActivity(new Intent(context, AboutActivity.class));
    }

}
