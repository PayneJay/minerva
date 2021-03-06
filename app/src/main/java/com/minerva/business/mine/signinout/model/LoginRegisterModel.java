package com.minerva.business.mine.signinout.model;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.minerva.MinervaApp;
import com.minerva.R;
import com.minerva.base.BaseBean;
import com.minerva.common.Constants;
import com.minerva.common.GlobalData;
import com.minerva.db.User;
import com.minerva.db.UserDao;
import com.minerva.network.RetrofitHelper;
import com.minerva.utils.ResourceUtil;
import com.minerva.utils.ToastUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

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
     * 三方登录
     *
     * @param params   三方认证信息
     * @param observer 回调
     */
    public void loginByOauth(OauthParams params, Observer<? super UserInfo> observer) {
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .loginByOauth(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 取消授权
     *
     * @param observer 回调
     */
    public void cancelSocial(Observer<UserInfo> observer) {
        User user = GlobalData.getInstance().getUser();
        int type = user.getOauth_type();
        int from = 1;
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .cancelSocial(type, from)
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
    public boolean isEmailValid(String email) {
        if (TextUtils.isEmpty(email) || email.length() == 0) {
            ToastUtil.showMsg(ResourceUtil.getString(R.string.login_please_input_account));
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ToastUtil.showMsg(ResourceUtil.getString(R.string.login_email_incorrect));
            return false;
        }
        return true;
    }

    /**
     * 说明:验证密码格式
     *
     * @param password icon_password
     * @return 密码格式正确或错误
     */
    public boolean isPasswordValid(String password) {
        if (TextUtils.isEmpty(password) || password.length() == 0) {
            ToastUtil.showMsg(ResourceUtil.getString(R.string.login_please_input_password));
            return false;
        } else if (password.length() < 6 || password.length() > 20) {
            ToastUtil.showMsg(ResourceUtil.getString(R.string.login_please_input_password_at_least_6));
            return false;
        }
        return true;
    }

    /**
     * 检查两次输入密码是否一致
     *
     * @param password   密码
     * @param confirmPwd 确认密码
     * @return
     */
    public boolean isPasswordIdentical(String password, String confirmPwd) {
        if (TextUtils.equals(password, confirmPwd)) {
            return true;
        }

        ToastUtil.showMsg(ResourceUtil.getString(R.string.register_check_password_identical));
        return false;
    }

    public void oauthByType(Context context, SHARE_MEDIA shareMedia, UMAuthListener umAuthListener) {
        boolean install = UMShareAPI.get(context).isInstall((Activity) context, shareMedia);
        if (!install) {
            switch (shareMedia) {
                case SINA:
                    ToastUtil.showMsg(ResourceUtil.getString(R.string.toast_not_install_weibo));
                    break;
                case QQ:
                    ToastUtil.showMsg(ResourceUtil.getString(R.string.toast_not_install_qq));
                    break;
                case WEIXIN:
                    ToastUtil.showMsg(ResourceUtil.getString(R.string.toast_not_install_wechat));
                    break;
            }
            return;
        }
        UMShareAPI.get(context).getPlatformInfo((Activity) context, shareMedia, umAuthListener);
    }

    /**
     * 保存用户信息
     *
     * @param user data
     */
    public void saveUserInfo(User user) {
        UserDao userDao = ((MinervaApp) Constants.application).getDaoSession().getUserDao();
        userDao.insertOrReplace(user);
    }

    public void updateUserInfo(User user) {
        UserDao userDao = ((MinervaApp) Constants.application).getDaoSession().getUserDao();
        userDao.update(user);
    }
}
