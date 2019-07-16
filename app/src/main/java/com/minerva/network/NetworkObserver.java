package com.minerva.network;


import android.widget.Toast;

import com.minerva.common.Constants;
import com.minerva.base.BaseBean;
import com.minerva.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class NetworkObserver<T extends BaseBean> implements Observer<T> {
    public NetworkObserver() {
    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(String msg);

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T response) {
        if (response.isSuccess()) {
            onSuccess(response);
        } else {
            ToastUtil.showMsg(response.getError());
            onFailure(response.getError());
        }
    }

    @Override
    public void onError(Throwable e) {
        onFailure(e.getMessage());
    }

    @Override
    public void onComplete() {

    }
}
