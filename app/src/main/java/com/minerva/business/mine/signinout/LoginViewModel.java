package com.minerva.business.mine.signinout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.signinout.model.LoginRegisterModel;
import com.minerva.business.mine.signinout.model.OauthParams;
import com.minerva.business.mine.signinout.model.UserInfo;
import com.minerva.common.Constants;
import com.minerva.common.EventMsg;
import com.minerva.db.User;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.CommonUtil;
import com.minerva.utils.ResourceUtil;
import com.minerva.utils.SPUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

public class LoginViewModel extends BaseViewModel {
    public ObservableField<String> email = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };

    public Toolbar.OnMenuItemClickListener menuItemClickListener = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if (item.getItemId() == R.id.register) {
                context.startActivity(new Intent(context, RegisterActivity.class));
            }
            return true;
        }
    };
    private ProgressDialog mProgressDialog;
    private int oauthType;
    private UMAuthListener umAuthListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Log.e(Constants.TAG, "platform : " + platform.getName());
            showDialog(ResourceUtil.getString(R.string.dialog_aouth_calling));
        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Log.e(Constants.TAG, "成功了 platform : " + platform.getName() + " action : " + action);
            Log.e(Constants.TAG, "data : " + CommonUtil.toJson(data));
            dismissDialog();
            if (data == null) {
                return;
            }

            if (platform.compareTo(SHARE_MEDIA.SINA) == 0) {
                oauthType = Constants.OauthType.TYPE_QQ_WEIBO;
            }
            if (platform.compareTo(SHARE_MEDIA.QQ) == 0) {
                oauthType = Constants.OauthType.TYPE_QQ;
            }
            if (platform.compareTo(SHARE_MEDIA.WEIXIN) == 0) {
                oauthType = Constants.OauthType.TYPE_WEIXIN;
            }

            loginByOauth(data);
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Log.e(Constants.TAG, "失败：platform : " + platform.getName() + " action : " + action + t.getMessage());
            dismissDialog();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Log.e(Constants.TAG, "取消了 platform : " + platform.getName() + " action : " + action);
            dismissDialog();
        }
    };

    LoginViewModel(Context context) {
        super(context);
        EventBus.getDefault().register(this);
        setLastLoginEmail();
    }

    public void signIn() {
        if (LoginRegisterModel.getInstance().isEmailValid(context, email.get())
                && LoginRegisterModel.getInstance().isPasswordValid(context, password.get())) {
            login();
        }
    }

    /**
     * 微博登录
     */
    public void loginWithWeibo() {
        LoginRegisterModel.getInstance().oauthByType(context, SHARE_MEDIA.SINA, umAuthListener);
    }

    /**
     * 微信登录
     */
    public void loginWithWeChat() {
        LoginRegisterModel.getInstance().oauthByType(context, SHARE_MEDIA.WEIXIN, umAuthListener);
    }

    /**
     * QQ登录
     */
    public void loginWithQQ() {
        LoginRegisterModel.getInstance().oauthByType(context, SHARE_MEDIA.QQ, umAuthListener);
    }

    /**
     * 设置最近一次登录的邮箱
     */
    private void setLastLoginEmail() {
        String lastEmail = (String) SPUtil.get(context, Constants.KeyExtra.LAST_LOGIN_EMAIL, "");
        email.set(lastEmail);
    }

    private void login() {
        showDialog(ResourceUtil.getString(R.string.login_verifying));

        oauthType = Constants.OauthType.TYPE_EMAIL;
        LoginRegisterModel.getInstance().doLogin(email.get(), password.get(), new NetworkObserver<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                dismissDialog();
                SPUtil.put(context, Constants.KeyExtra.LAST_LOGIN_EMAIL, email.get());
                loginSuccess(userInfo);
            }

            @Override
            public void onFailure(String msg) {
                dismissDialog();
            }
        });
    }

    private void loginSuccess(UserInfo userInfo) {
        Toast.makeText(context, ResourceUtil.getString(R.string.login_success), Toast.LENGTH_SHORT).show();
        User user = userInfo.getUser();
        if (user != null) {
            user.setOauth_type(oauthType);
            LoginRegisterModel.getInstance().saveUserInfo(user);
            EventBus.getDefault().post(new EventMsg(Constants.EventMsgKey.LOGIN_SUCCESS));
            ((BaseActivity) context).finish();
        }
    }

    /**
     * 三方登录
     *
     * @param data 认证信息
     */
    private void loginByOauth(Map<String, String> data) {
        OauthParams oauthParams = new OauthParams();
        oauthParams.setUid(data.get("uid"));
        oauthParams.setType(oauthType + "");
        oauthParams.setToken(data.get("access_token"));
        oauthParams.setName(data.get("name"));
        oauthParams.setUnion_id(data.get("unionid"));
        oauthParams.setImage(data.get("profile_image_url"));
        oauthParams.setFrom("1");

        LoginRegisterModel.getInstance().loginByOauth(oauthParams, new NetworkObserver<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                Log.i(Constants.TAG, userInfo.getUser().getToken());
                loginSuccess(userInfo);
            }

            @Override
            public void onFailure(String msg) {
                Log.e(Constants.TAG, "loginFailure : " + msg);
            }
        });
    }

    /**
     * 说明:show一个Dialog
     */
    private void showDialog(String text) {
        mProgressDialog = ProgressDialog.show(context, ResourceUtil.getString(R.string.dialog_title_note), text, false);
    }

    private void dismissDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
