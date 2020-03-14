package com.minerva.business;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.guide.GuideSubscribeActivity;
import com.minerva.business.home.HomeActivity;
import com.minerva.common.Constants;
import com.minerva.common.GlobalData;
import com.minerva.db.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class SplashActivity extends BaseActivity<SplashActivity.SplashViewModel> {

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
            getStaticData();
        }

        /**
         * 从远端获取静态参数
         */
        private void getStaticData() {
            OkHttpClient client = new OkHttpClient();
            final Request request = new Request.Builder()
                    .get()
                    .url("https://hwemails.cn/data.json")
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(Constants.TAG, "onFailure: " + e.getMessage());
                    finish();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    ResponseBody body = response.body();
                    if (body != null) {
                        String json = body.string();
                        Log.e(Constants.TAG, "onResponse: " + json);
                        try {
                            JSONObject jObject = new JSONObject(json);
                            Constants.HOST = jObject.getString("host");
                            Constants.shareBaseUrl = jObject.getString("shareUrl");
                            Constants.TOKEN = jObject.getString("token");
                            body.close();
                            finishSplash();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        private void finishSplash() {
            User user = GlobalData.getInstance().getUser();
            if (user != null && user.getIs_new()) {
                Intent intent = new Intent(context, GuideSubscribeActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(context, HomeActivity.class);
                startActivity(intent);
            }
            finish();
        }

        public int getImgUrl() {
            Random random = new Random();
            int i = random.nextInt(imgs.length);
            Log.e(Constants.TAG, i + "----" + imgs[i]);
            return imgs[i];
        }
    }
}
