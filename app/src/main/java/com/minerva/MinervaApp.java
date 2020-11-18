package com.minerva;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.minerva.common.Constants;
import com.minerva.db.DaoMaster;
import com.minerva.db.DaoSession;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

public class MinervaApp extends Application {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        Constants.application = this;
        setupLeakCanary();
        setupUMeng();
        setupXFYun();
        setupDatabase();
    }

    private void setupLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }

        mRefWatcher = LeakCanary.install(this);
    }

    /**
     * 如果是需要监测指定的类的内存泄漏情况，需要用到RefWatcher对象，这里提供该对象便于使用
     *
     * @param context 上下文
     * @return RefWatcher
     */
    public static RefWatcher getRefWatcher(Context context) {
        MinervaApp application = (MinervaApp) context.getApplicationContext();
        return application.mRefWatcher;
    }

    static {
        PlatformConfig.setWeixin("wx22cea56d9fb6fad6", "85d78433f330f4f67346c4f4095167c7");
        PlatformConfig.setSinaWeibo("214879162", "5d90e865ad221deb1d1c5c1c07a2a074", "http://sns.whalecloud.com");
        PlatformConfig.setQQZone("1108243856", "cfLgQhTaBLKgQhu5");
    }

    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public DaoSession getNewSession() {
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "minerva.db");
        mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
    }

    private void setupXFYun() {
        // 将“12345678”替换成您申请的APPID，申请地址：http://www.xfyun.cn
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5ce399db");
    }

    /**
     * 初始化友盟相关
     */
    private void setupUMeng() {
        UMConfigure.init(this, "5c776a0df1f556755c001104"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
        UMShareAPI.get(this);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        // 将默认Session间隔时长改为40秒。
        MobclickAgent.setSessionContinueMillis(1000 * 40);
    }

}
