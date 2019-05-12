package com.minerva;

import android.app.Application;

import com.minerva.common.Constants;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

public class MinervaApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Constants.application = this;

        initUmeng();
    }

    /**
     * 初始化友盟相关
     */
    private void initUmeng() {
        UMConfigure.init(this, "5c776a0df1f556755c001104"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        UMShareAPI.get(this);
    }

    static {
        PlatformConfig.setWeixin("wx22cea56d9fb6fad6", "85d78433f330f4f67346c4f4095167c7");
        PlatformConfig.setSinaWeibo("214879162", "85d78433f330f4f67346c4f4095167c7", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
}
