package com.minerva;

import android.app.Application;

public class MinervaApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Constants.application = this;
    }
}
