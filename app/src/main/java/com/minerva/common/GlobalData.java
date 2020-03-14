package com.minerva.common;

import android.text.TextUtils;

import com.minerva.MinervaApp;
import com.minerva.business.mine.signinout.model.LoginRegisterModel;
import com.minerva.db.User;
import com.minerva.db.UserDao;
import com.minerva.utils.SPUtil;

import java.util.List;

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
        User user = getUser();
        if (null == user) {
            return false;
        }
        return !TextUtils.isEmpty(user.getUid()) && !TextUtils.isEmpty(user.getToken());
    }

    public User getUser() {
        UserDao userDao = ((MinervaApp) Constants.application).getDaoSession().getUserDao();
        List<User> users = userDao.loadAll();
        if (users == null || users.size() == 0) {
            return null;
        }

        return users.get(0);
    }

    public String getUid() {
        User user = getUser();
        return (null == user) ? "10.2.62.44" : user.getUid();
    }

    public String getToken() {
        User user = getUser();
        return (null == user) ? Constants.TOKEN : user.getToken();
    }

    /**
     * 清空登录状态，用户信息
     */
    public void clear() {
        UserDao userDao = ((MinervaApp) Constants.application).getDaoSession().getUserDao();
        userDao.deleteAll();
    }
}
