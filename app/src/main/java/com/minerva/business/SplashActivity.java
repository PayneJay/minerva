package com.minerva.business;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.home.HomeActivity;
import com.minerva.common.Constants;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends BaseActivity<SplashActivity.SplashViewModel> {
    private Disposable mDisposable;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_splash_layout;
    }

    @Override
    protected SplashViewModel getViewModel() {
        return new SplashViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.specialVM;
    }

    @Override
    protected void onDestroy() {
        if (mDisposable != null) {
            mDisposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    public class SplashViewModel extends BaseViewModel {
        private int imgs[] = {R.mipmap.img_splash1, R.mipmap.img_splash2, R.mipmap.img_splash3,
                R.mipmap.img_splash4, R.mipmap.img_splash6, R.mipmap.img_splash7,
                R.mipmap.img_splash8, R.mipmap.img_splash9};

        SplashViewModel(Context context) {
            super(context);
            ((SplashActivity) context).getWindow().setBackgroundDrawableResource(getImgUrl());
            delay();
        }

        private void delay() {
            Observable.just("Welcome")
                    //延时三秒，第一个参数是数值，第二个参数是事件单位
                    .delay(3, TimeUnit.SECONDS)
                    // Run on a background thread
                    .subscribeOn(Schedulers.io())
                    // Be notified on the main thread
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<String>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mDisposable = d;
                        }

                        @Override
                        public void onNext(String s) {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
        }

        public int getImgUrl() {
            Random random = new Random();
            int i = random.nextInt(imgs.length);
            Log.e(Constants.TAG, i + "----" + imgs[i]);
            return imgs[i];
        }
    }
}
