package com.minerva.business.mine.user;

import android.content.Context;
import android.databinding.ObservableField;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.signinout.model.LoginRegisterModel;
import com.minerva.business.mine.signinout.model.UserInfo;
import com.minerva.common.Constants;
import com.minerva.common.EventMsg;
import com.minerva.db.User;
import com.minerva.network.NetworkObserver;
import com.minerva.network.RetrofitHelper;
import com.minerva.utils.ResourceUtil;
import com.minerva.utils.ToastUtil;
import com.minerva.widget.Loading;

import org.greenrobot.eventbus.EventBus;

import java.text.MessageFormat;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpdateEmailViewModel extends BaseViewModel {
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> content = new ObservableField<>("");
    private IDialogClickListener listener;
    private Loading loading;

    UpdateEmailViewModel(Context context) {
        super(context);
    }

    public IDialogClickListener getListener() {
        return listener;
    }

    public void setListener(IDialogClickListener listener) {
        this.listener = listener;
    }

    public void onConfirmClick() {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(content.get()).matches()) {
            ToastUtil.showMsg(ResourceUtil.getString(R.string.login_email_incorrect));
            return;
        }
        updateEmail();
    }

    public void onCancelClick() {
        if (listener != null) {
            listener.cancel();
        }
    }

    private void updateEmail() {
        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .updateUserEmail(content.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        loading.dismiss();
                        ToastUtil.showMsg(MessageFormat.format(ResourceUtil.getString(R.string.toast_send_verify_email), content.get()));
                        User user = userInfo.getUser();
                        if (user != null) {
                            LoginRegisterModel.getInstance().updateUserInfo(user);
                            EventBus.getDefault().post(new EventMsg(Constants.EventMsgKey.LOGIN_SUCCESS));
                        }
                        if (listener != null) {
                            listener.confirm();
                        }
                    }

                    @Override
                    public void onFailure(String msg) {
                        loading.dismiss();
                    }
                });
    }
}
