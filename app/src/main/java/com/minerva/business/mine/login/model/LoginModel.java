package com.minerva.business.mine.login.model;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;
import com.minerva.utils.ResouceUtils;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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


    public void doLogin(String email, String password, Observer<? super UserInfo> observer) {
        LoginParams params = new LoginParams(email, password);
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 说明:验证邮箱格式
     *
     * @param email 邮箱地址
     * @return 邮箱地址格式正确或错误
     */
    public boolean isEmailValid(Context context, String email) {
        if (TextUtils.isEmpty(email) || email.length() == 0) {
            showToast(context, ResouceUtils.getString(R.string.login_please_input_account));
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast(context, ResouceUtils.getString(R.string.login_email_incorrect));
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
            showToast(context, ResouceUtils.getString(R.string.login_please_input_password));
            return false;
        } else if (password.length() < 6) {
            showToast(context, ResouceUtils.getString(R.string.login_please_input_password_at_least_6));
            return false;
        }
        return true;
    }

    private void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
