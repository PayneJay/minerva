package com.minerva.business.mine;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.about.AboutActivity;
import com.minerva.business.mine.login.LoginActivity;
import com.minerva.common.GlobalData;
import com.minerva.utils.ResouceUtils;

import static com.minerva.common.Constants.showToast;

public class MyViewModel extends BaseViewModel {
    public ObservableField<String> userName = new ObservableField<>(ResouceUtils.getString(R.string.mine_click_login));

    public MyViewModel(Context context) {
        super(context);
        if (GlobalData.isLogin()) {
            userName.set("这里展示用户名");
        }
    }

    public void goLogin() {
        if (GlobalData.isLogin()) {
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
