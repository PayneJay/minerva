package com.minerva.common;

import com.minerva.utils.SPUtils;

public class GlobalData {
    private static GlobalData instance;

    public static GlobalData getInstance() {
        if (instance == null) {
            synchronized (GlobalData.class) {
                instance = new GlobalData();
            }
        }
        return instance;
    }

    public static boolean isLogin() {
        return (boolean) SPUtils.get(Constants.application, Constants.LoginInfo.IS_LOGIN, false);
    }
}
