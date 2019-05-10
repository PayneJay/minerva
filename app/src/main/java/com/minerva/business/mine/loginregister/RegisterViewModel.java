package com.minerva.business.mine.loginregister;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.os.Process;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.business.SplashActivity;
import com.minerva.business.article.detail.AddToJournalViewModel;
import com.minerva.business.mine.loginregister.model.LoginRegisterModel;
import com.minerva.business.mine.loginregister.model.UserInfo;
import com.minerva.common.Constants;
import com.minerva.common.EventMsg;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.ResourceUtils;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RegisterViewModel extends BaseViewModel implements RegisterVerifyViewModel.IClickAction {
    public ObservableField<String> email = new ObservableField<>();
    public ObservableField<String> userName = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();
    public ObservableField<String> pwdConfirm = new ObservableField<>();
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ((BaseActivity) context).finish();
        }
    };
    private ProgressDialog mProgressDialog;
    private Dialog verifyDialog;

    RegisterViewModel(Context context) {
        super(context);
    }

    public void register() {
        if (LoginRegisterModel.getInstance().isEmailValid(context, email.get())
                && LoginRegisterModel.getInstance().isPasswordValid(context, password.get())
                && LoginRegisterModel.getInstance().isPasswordIdentical(context, password.get(), pwdConfirm.get())) {
            showDialog();

            LoginRegisterModel.getInstance().doRegister(email.get(), userName.get(), password.get(),
                    new NetworkObserver<BaseBean>() {
                        @Override
                        public void onSuccess(BaseBean baseBean) {
                            mProgressDialog.dismiss();
                            showAddJournalDialog();
                        }

                        @Override
                        public void onFailure(String msg) {
                            mProgressDialog.dismiss();
                        }
                    });
        }
    }

    private void showDialog() {
        mProgressDialog = new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(ResourceUtils.getString(R.string.toast_registing));
        mProgressDialog.show();
    }

    private void showAddJournalDialog() {
        if (verifyDialog == null) {
            verifyDialog = new Dialog(context, R.style.SlideBottomDialogStyle);
        }

        RegisterVerifyViewModel viewModel = new RegisterVerifyViewModel(context, this);
        viewModel.setRegisterEmail(email.get());
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_register_verify_layout, null, false);
        binding.setVariable(BR.registerVerifyVM, viewModel);
        binding.executePendingBindings();
        verifyDialog.setContentView(binding.getRoot());
        verifyDialog.show();
    }

    @Override
    public void onBack() {
        if (verifyDialog != null) {
            verifyDialog.dismiss();
        }
    }

    @Override
    public void finishVerify() {
        if (verifyDialog != null) {
            verifyDialog.dismiss();
        }

        LoginRegisterModel.getInstance().getCheckConfirmObserver(email.get(), password.get())
                .observeOn(Schedulers.io())
                .flatMap(new Function<BaseBean, ObservableSource<UserInfo>>() {
                    @Override
                    public ObservableSource<UserInfo> apply(BaseBean baseBean) {
                        if (!baseBean.isSuccess()) {
                            Toast.makeText(context, baseBean.getError(), Toast.LENGTH_SHORT).show();
                            return null;
                        }
                        return LoginRegisterModel.getInstance().getLoginObserver(email.get(), password.get());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        UserInfo.UserBean user = userInfo.getUser();
                        if (user != null) {
                            LoginRegisterModel.getInstance().saveUserInfo(context, user);
                            restartApp();
                        }
                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
    }

    private void restartApp() {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // 杀掉进程
        Process.killProcess(Process.myPid());
        System.exit(0);
    }
}
