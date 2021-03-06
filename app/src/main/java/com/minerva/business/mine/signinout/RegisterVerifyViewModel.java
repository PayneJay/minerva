package com.minerva.business.mine.signinout;

import android.content.Context;
import android.databinding.ObservableField;
import android.view.View;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.business.mine.signinout.model.LoginRegisterModel;
import com.minerva.network.NetworkObserver;
import com.minerva.utils.ResourceUtil;
import com.minerva.utils.ToastUtil;

import java.text.MessageFormat;

public class RegisterVerifyViewModel extends BaseViewModel {
    public ObservableField<String> descText = new ObservableField<>();
    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (actionListener != null) {
                actionListener.onBack();
            }
        }
    };
    private IClickAction actionListener;
    private String email;

    RegisterVerifyViewModel(Context context, IClickAction listener) {
        super(context);
        this.actionListener = listener;
    }

    void setRegisterEmail(String registerEmail) {
        this.email = registerEmail;
        descText.set(MessageFormat.format(ResourceUtil.getString(R.string.register_verify_desc), registerEmail));
    }

    public void sendEmailAgain() {
        LoginRegisterModel.getInstance().resendRegister(email, new NetworkObserver<BaseBean>() {
            @Override
            public void onSuccess(BaseBean baseBean) {
                ToastUtil.showMsg(ResourceUtil.getString(R.string.resend_success));
            }

            @Override
            public void onFailure(String msg) {

            }
        });
    }

    public void finishVerify() {
        if (actionListener != null) {
            actionListener.finishVerify();
        }
    }

    public interface IClickAction {
        void onBack();

        void finishVerify();
    }
}
