package com.minerva.business.mine.user;

import android.content.Context;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.signinout.model.LoginRegisterModel;
import com.minerva.business.mine.signinout.model.UserInfo;
import com.minerva.common.Constants;
import com.minerva.common.EventMsg;
import com.minerva.network.NetworkObserver;
import com.minerva.network.RetrofitHelper;
import com.minerva.utils.ResourceUtils;
import com.minerva.widget.Loading;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpdateNameViewModel extends BaseViewModel {
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> content = new ObservableField<>("");
    private IDialogClickListener listener;
    private Loading loading;

    UpdateNameViewModel(Context context) {
        super(context);
    }

    public IDialogClickListener getListener() {
        return listener;
    }

    public void setListener(IDialogClickListener listener) {
        this.listener = listener;
    }

    public void onConfirmClick() {
        if (TextUtils.isEmpty(content.get())) {
            Toast.makeText(context, ResourceUtils.getString(R.string.please_check_your_content_empty), Toast.LENGTH_SHORT).show();
            return;
        }
        updateUserName();
    }

    public void onCancelClick() {
        if (listener != null) {
            listener.cancel();
        }
    }

    private void updateUserName() {
        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .updateUserInfo(content.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        loading.dismiss();
                        UserInfo.UserBean user = userInfo.getUser();
                        if (user != null) {
                            LoginRegisterModel.getInstance().saveUserInfo(context, user);
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
