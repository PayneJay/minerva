package com.minerva.common;

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
        return true;
    }
}
