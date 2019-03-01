package com.minerva.business.mine.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.ObservableField;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;
import com.minerva.utils.SPUtils;

import java.util.Timer;
import java.util.TimerTask;

public class LoginViewModel extends BaseViewModel {
    public ObservableField<String> email = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    private ProgressDialog mProgressDialog;

    LoginViewModel(Context context) {
        super(context);
    }

    public void signIn() {
        if (LoginModel.getInstance().isEmailValid(context, email.get())
                && LoginModel.getInstance().isPasswordValid(context, password.get())
                && LoginModel.getInstance().isMatch(context, email.get(), password.get())) {
            login();
        }
    }

    /**
     * 说明:登录
     * <p>
     * 这里为了展示对话框,将对话框固定显示了3秒
     */
    private void login() {
        showDialog();

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                SPUtils.put(context, Constants.LoginInfo.IS_LOGIN, true);
                mProgressDialog.dismiss();
                ((BaseActivity) context).finish();
            }
        };
        timer.schedule(timerTask, 3000);
    }

    /**
     * 说明:show一个Dialog
     */
    private void showDialog() {
        mProgressDialog = new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("正在验证...");
        mProgressDialog.show();
    }
}
