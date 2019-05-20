package com.minerva.business.mine.signinout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.signinout.model.LoginRegisterModel;
import com.minerva.business.mine.signinout.model.UserInfo;
import com.minerva.common.Constants;
import com.minerva.common.EventMsg;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.ResourceUtils;
import com.minerva.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

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
     * 设置最近一次登录的邮箱
     */
    private void setLastLoginEmail() {
        String lastEmail = (String) SPUtils.get(context, Constants.KeyExtra.LAST_LOGIN_EMAIL, "");
        email.set(lastEmail);
    }

    private void login() {
        showDialog();

        LoginRegisterModel.getInstance().doLogin(email.get(), password.get(), new NetworkObserver<UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                mProgressDialog.dismiss();
                Toast.makeText(context, ResourceUtils.getString(R.string.login_success), Toast.LENGTH_SHORT).show();
                UserInfo.UserBean user = userInfo.getUser();
                if (user != null) {
                    LoginRegisterModel.getInstance().saveUserInfo(context, user);
                    EventBus.getDefault().post(new EventMsg(Constants.EventMsgKey.LOGIN_SUCCESS));
                    ((BaseActivity) context).finish();
                }
            }

            @Override
            public void onFailure(String msg) {
                mProgressDialog.dismiss();
            }
        });
    }

    /**
     * 说明:show一个Dialog
     */
    private void showDialog() {
        mProgressDialog = new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(ResourceUtils.getString(R.string.login_verifying));
        mProgressDialog.show();
    }
}
