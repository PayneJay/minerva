package com.minerva.common;

import android.text.TextUtils;

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

    /**
     * 判断是否是登录状态
     *
     * @return
     */
    public boolean isLogin() {
        String uid = (String) SPUtils.get(Constants.application, Constants.UserInfoKey.USER_ID, "");
        String token = (String) SPUtils.get(Constants.application, Constants.UserInfoKey.USER_TOKEN, "");
        return !TextUtils.isEmpty(uid) && !TextUtils.isEmpty(token);
    }

    public String getUid() {
        return (String) SPUtils.get(Constants.application, Constants.UserInfoKey.USER_ID, "10.2.62.44");
    }

    public String getToken() {
        return (String) SPUtils.get(Constants.application, Constants.UserInfoKey.USER_TOKEN, "tuicool");
    }

    /**
     * 清空登录状态，用户信息
     */
    public void clear() {
        SPUtils.put(Constants.application, Constants.UserInfoKey.USER_ID, "");
        SPUtils.put(Constants.application, Constants.UserInfoKey.USER_TOKEN, "");

        SPUtils.put(Constants.application, Constants.UserInfoKey.USER_PROFILE, "");
        SPUtils.put(Constants.application, Constants.UserInfoKey.USER_NAME, "");
        SPUtils.put(Constants.application, Constants.UserInfoKey.USER_EMAIL, "");
        SPUtils.put(Constants.application, Constants.UserInfoKey.WEIBO, "");
        SPUtils.put(Constants.application, Constants.UserInfoKey.QQ, "");
        SPUtils.put(Constants.application, Constants.UserInfoKey.WECHAT, "");
    }
}
