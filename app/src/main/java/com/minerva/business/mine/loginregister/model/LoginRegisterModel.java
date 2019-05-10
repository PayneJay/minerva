package com.minerva.business.mine.loginregister.model;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseBean;
import com.minerva.common.Constants;
import com.minerva.network.RetrofitHelper;
import com.minerva.utils.ResourceUtils;
import com.minerva.utils.SPUtils;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginRegisterModel {
    private static LoginRegisterModel instance;


    private LoginRegisterModel() {
    }

    public static LoginRegisterModel getInstance() {
        if (instance == null) {
            instance = new LoginRegisterModel();
        }
        return instance;
    }

    /**
     * 登录
     *
     * @param email    邮箱
     * @param password 密码
     * @param observer 回调
     */
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
     * 获取登录Observer
     *
     * @param email    邮箱
     * @param password 密码
     * @return
     */
    public Observable<UserInfo> getLoginObserver(String email, String password) {
        LoginParams params = new LoginParams(email, password);
        return RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .login(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 邮箱注册
     *
     * @param email    邮箱
     * @param name     用户名
     * @param password 密码
     * @param observer 回调
     */
    public void doRegister(String email, String name, String password, Observer<? super BaseBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .registerByEmail(email, name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 邮箱二次验证
     *
     * @param email    邮箱
     * @param password 密码
     */
    public Observable<BaseBean> getCheckConfirmObserver(String email, String password) {
        return RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .checkConfirm(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 再次发送邮件
     *
     * @param email    邮箱
     * @param observer 回调
     */
    public void resendRegister(String email, Observer<? super BaseBean> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .resendRegister(email)
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
            showToast(context, ResourceUtils.getString(R.string.login_please_input_account));
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast(context, ResourceUtils.getString(R.string.login_email_incorrect));
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
            showToast(context, ResourceUtils.getString(R.string.login_please_input_password));
            return false;
        } else if (password.length() < 6 || password.length() > 20) {
            showToast(context, ResourceUtils.getString(R.string.login_please_input_password_at_least_6));
            return false;
        }
        return true;
    }

    public boolean isPasswordIdentical(Context context, String password, String confirmPwd) {
        if (TextUtils.equals(password, confirmPwd)) {
            return true;
        }

        showToast(context, ResourceUtils.getString(R.string.register_check_password_identical));
        return false;
    }

    public void saveUserInfo(Context context, UserInfo.UserBean user) {
        SPUtils.put(context, Constants.UserInfoKey.USER_ID, user.getUid());
        SPUtils.put(context, Constants.UserInfoKey.USER_TOKEN, user.getToken());

        SPUtils.put(context, Constants.UserInfoKey.USER_PROFILE, user.getProfile());
        SPUtils.put(context, Constants.UserInfoKey.USER_NAME, user.getName());
        SPUtils.put(context, Constants.UserInfoKey.USER_EMAIL, user.getEmail());
        SPUtils.put(context, Constants.UserInfoKey.WEIBO, user.getWeibo_name());
        SPUtils.put(context, Constants.UserInfoKey.QQ, user.getQq_name());
        SPUtils.put(context, Constants.UserInfoKey.WECHAT, user.getWeixin_name());
    }

    private void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
