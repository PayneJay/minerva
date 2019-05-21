package com.minerva.common;

import android.text.TextUtils;

import com.minerva.utils.SPUtil;

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
        String uid = (String) SPUtil.get(Constants.application, Constants.UserInfoKey.USER_ID, "");
        String token = (String) SPUtil.get(Constants.application, Constants.UserInfoKey.USER_TOKEN, "");
        return !TextUtils.isEmpty(uid) && !TextUtils.isEmpty(token);
    }

    public String getUid() {
        return (String) SPUtil.get(Constants.application, Constants.UserInfoKey.USER_ID, "10.2.62.44");
    }

    public String getToken() {
        return (String) SPUtil.get(Constants.application, Constants.UserInfoKey.USER_TOKEN, "tuicool");
    }

    /**
     * 清空登录状态，用户信息
     */
    public void clear() {
        SPUtil.put(Constants.application, Constants.UserInfoKey.USER_ID, "");
        SPUtil.put(Constants.application, Constants.UserInfoKey.USER_TOKEN, "");

        SPUtil.put(Constants.application, Constants.UserInfoKey.USER_PROFILE, "");
        SPUtil.put(Constants.application, Constants.UserInfoKey.USER_NAME, "");
        SPUtil.put(Constants.application, Constants.UserInfoKey.USER_EMAIL, "");
        SPUtil.put(Constants.application, Constants.UserInfoKey.WEIBO, "");
        SPUtil.put(Constants.application, Constants.UserInfoKey.QQ, "");
        SPUtil.put(Constants.application, Constants.UserInfoKey.WECHAT, "");
    }
}
