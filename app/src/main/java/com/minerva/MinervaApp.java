package com.minerva;

import android.app.Application;

import com.minerva.common.Constants;

public class MinervaApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Constants.application = this;
    }
}
