package com.minerva.business.mine.user;

import android.content.Context;
import android.databinding.ObservableField;
import android.text.TextUtils;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.base.BaseBean;
import com.minerva.base.BaseViewModel;
import com.minerva.common.Constants;
import com.minerva.network.NetworkObserver;
import com.minerva.network.RetrofitHelper;
import com.minerva.utils.ResourceUtil;
import com.minerva.widget.Loading;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpdatePwdViewModel extends BaseViewModel {
    public ObservableField<String> title = new ObservableField<>();
    public ObservableField<String> currentPwd = new ObservableField<>("");
    public ObservableField<String> newPwd = new ObservableField<>("");
    public ObservableField<String> repeatPwd = new ObservableField<>("");
    private IDialogClickListener listener;
    private Loading loading;

    UpdatePwdViewModel(Context context) {
        super(context);
    }

    public IDialogClickListener getListener() {
        return listener;
    }

    public void setListener(IDialogClickListener listener) {
        this.listener = listener;
    }

    public void onConfirmClick() {
        if (TextUtils.isEmpty(newPwd.get()) || newPwd.get().length() < 6) {
            Toast.makeText(context, ResourceUtil.getString(R.string.toast_journal_char_error), Toast.LENGTH_SHORT).show();
            return;
        }

        if (!TextUtils.equals(newPwd.get(), repeatPwd.get())) {
            Toast.makeText(context, ResourceUtil.getString(R.string.register_check_password_identical), Toast.LENGTH_SHORT).show();
            return;
        }

        updatePassword();
    }

    public void onCancelClick() {
        if (listener != null) {
            listener.cancel();
        }
    }

    private void updatePassword() {
        if (loading == null) {
            loading = new Loading.Builder(context).show();
        } else {
            loading.show();
        }
        RetrofitHelper.getInstance(Constants.RequestMethod.METHOD_POST, null)
                .getServer()
                .updatePassword(currentPwd.get(), newPwd.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetworkObserver<BaseBean>() {
                    @Override
                    public void onSuccess(BaseBean baseBean) {
                        loading.dismiss();
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
