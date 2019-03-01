package com.minerva.business.mine.login;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.minerva.common.Constants;
import com.minerva.utils.SPUtils;

public class LoginModel {
    private static LoginModel instance;


    private LoginModel() {
    }

    public static LoginModel getInstance() {
        if (instance == null) {
            instance = new LoginModel();
        }
        return instance;
    }


    /**
     * @param email    邮箱
     * @param password 密码
     * @return 用户名密码是否匹配
     */
    public boolean isMatch(Context context, String email, String password) {
        if (!Constants.LoginInfo.USER_NAME.equals(email) || !Constants.LoginInfo.PASSWORD.equals(password)) {
            showToast(context, "账号或密码错误！");
            return false;
        }

        return true;
    }


    /**
     * 说明:验证邮箱格式
     *
     * @param email 邮箱地址
     * @return 邮箱地址格式正确或错误
     */
    public boolean isEmailValid(Context context, String email) {
        if (TextUtils.isEmpty(email) || email.length() == 0) {
            showToast(context, "请输入邮箱!");
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast(context, "邮箱格式不正确!");
            return false;
        }
        return true;

    }

    /**
     * 说明:验证密码格式
     *
     * @param context
     * @param password icon_password
     * @return 密码格式正确或错误
     */
    public boolean isPasswordValid(Context context, String password) {
        if (TextUtils.isEmpty(password) || password.length() == 0) {
            showToast(context, "请输入密码!");
            return false;
        } else if (password.length() < 6) {
            showToast(context, "请输入至少6位密码!");
            return false;
        }
        return true;
    }

    private void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
