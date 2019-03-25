package com.minerva.business.mine.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.ObservableField;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.login.model.LoginModel;
import com.minerva.business.mine.login.model.UserInfo;
import com.minerva.common.Constants;
import com.minerva.common.EventMsg;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.ResourceUtils;
import com.minerva.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

public class LoginViewModel extends BaseViewModel {
    public ObservableField<String> email = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    private ProgressDialog mProgressDialog;

    LoginViewModel(Context context) {
        super(context);
    }

    public void signIn() {
        if (LoginModel.getInstance().isEmailValid(context, email.get())
                && LoginModel.getInstance().isPasswordValid(context, password.get())) {
            login();
        }
    }

    private void login() {
        showDialog();

        LoginModel.getInstance().doLogin(email.get(), password.get(), new NetworkObserver<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                mProgressDialog.dismiss();
                Toast.makeText(context, ResourceUtils.getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                UserInfo.UserBean user = userInfo.getUser();
                if (user != null) {
                    saveUserInfo(user);
                    EventBus.getDefault().post(new EventMsg(Constants.EventMsgKey.LOGIN_SUCCESS));
                    ((BaseActivity) context).finish();
                }
            }

            @Override
            public void onFailure() {
                mProgressDialog.dismiss();
            }
        });
    }

    private void saveUserInfo(UserInfo.UserBean user) {
        SPUtils.put(context, Constants.UserInfoKey.USER_ID, user.getUid());
        SPUtils.put(context, Constants.UserInfoKey.USER_TOKEN, user.getToken());

        SPUtils.put(context, Constants.UserInfoKey.USER_PROFILE, user.getProfile());
        SPUtils.put(context, Constants.UserInfoKey.USER_NAME, user.getName());
        SPUtils.put(context, Constants.UserInfoKey.USER_EMAIL, user.getEmail());
        SPUtils.put(context, Constants.UserInfoKey.WEIBO, user.getWeibo_name());
        SPUtils.put(context, Constants.UserInfoKey.QQ, user.getQq_name());
        SPUtils.put(context, Constants.UserInfoKey.WECHAT, user.getWeixin_name());
    }

    /**
     * 说明:show一个Dialog
     */
    private void showDialog() {
        mProgressDialog = new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(ResourceUtils.getString(R.string.login_verifing));
        mProgressDialog.show();
    }
}
