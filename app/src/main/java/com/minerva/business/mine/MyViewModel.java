package com.minerva.business.mine;

import android.content.Context;
import android.content.Intent;

import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.login.LoginActivity;

public class MyViewModel extends BaseViewModel {
    public MyViewModel(Context context) {
        super(context);
    }

    public void goLogin() {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    public void goUnRead() {
    }

    public void goCollection() {
    }

    public void goJournal() {
    }

    public void goNotification() {
    }

    public void goHistory() {
    }

    public void switchModel() {
    }

    public void readOffline() {
    }

    public void checkUpgrade() {
    }

    public void goFeedback() {
    }

    public void aboutUs() {
    }
}
