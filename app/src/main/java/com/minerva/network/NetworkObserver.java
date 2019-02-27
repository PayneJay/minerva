package com.minerva.network;


import android.util.Log;

import com.minerva.common.Constants;
import com.minerva.base.BaseBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by nayibo on 2018/3/26.
 */

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
            onSuccess(response);
        } else {
            onFailure();
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.e(Constants.TAG, "onError===>" + e.getMessage());
        onFailure();
    }

    @Override
    public void onComplete() {

    }
}
