package com.minerva.network;


import android.util.Log;

import com.minerva.common.Constants;
import com.minerva.base.BaseBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class NetworkObserver<T extends BaseBean> implements Observer<T> {
    public NetworkObserver() {
    }

    public abstract void onSuccess(T t);

    public abstract void onFailure();

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T response) {
        if (response.isSuccess()) {
            Log.e(Constants.TAG, "onSuccess===>");
            onSuccess(response);
        } else {
            Log.e(Constants.TAG, "onFailure===>");
            onFailure();
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(Constants.TAG, "onError===>" + e.toString());
        onFailure();
    }

    @Override
    public void onComplete() {

    }
}
