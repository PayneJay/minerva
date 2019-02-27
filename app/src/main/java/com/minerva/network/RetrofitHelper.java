package com.minerva.network;

import android.util.Base64;
import android.util.Log;

import com.minerva.common.Constants;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nayibo on 2017/7/25.
 */

public class RetrofitHelper {
    private Retrofit mRetrofit;
    private String mMethod;
    private boolean mIsNeedLoginString;

    private RetrofitHelper(String method, HashMap<String, String> headerMap, String baseType, boolean isNeedLoginString) {
        this.mMethod = method;
        this.mIsNeedLoginString = isNeedLoginString;
        init();
    }

    public static RetrofitHelper getInstance(String method, HashMap<String, String> headerMap) {
        return new RetrofitHelper(method, headerMap, "", true);
    }

    public static RetrofitHelper getInstance(String method, HashMap<String, String> headerMap, String baseType) {
        return new RetrofitHelper(method, headerMap, baseType, true);
    }

    public static RetrofitHelper getInstance(String method, HashMap<String, String> headerMap, String baseType, boolean isNeedLoginString) {
        return new RetrofitHelper(method, headerMap, baseType, isNeedLoginString);
    }

    private void init() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new NetworkInterceptor(null, getHeaders(mMethod), mIsNeedLoginString))
                .addInterceptor(new LogInterceptor())
                .sslSocketFactory(TrustAllCerts.createSSLSocketFactory())
                .hostnameVerifier(new TrustAllCerts.TrustAllHostnameVerifier())
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder().client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(getHost()).build();
    }

    public RetrofitService getServer() {
        return mRetrofit.create(RetrofitService.class);
    }

    private static String getHost() {
        return Constants.HOST;
    }

    public static HashMap<String, String> getHeaders(String method) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Basic MTAuMi42Mi40NDp0dWljb29s");
        if (method.equals(Constants.RequestMethod.METHOD_POST)) {
            headers.put("Accept", "application/json");
            headers.put("Content-type", "application/json");
        }
        return headers;
    }

    private static String getBasicAuth() {
        String userName = "0.0.0.0";
        String passWord = "tuicool";
        String base64;
        base64 = Base64.encodeToString((userName + ":" + passWord).getBytes(), Base64.DEFAULT);
        Log.i(Constants.TAG, "auth===" + base64);
        return "Basic " + base64;
    }

}
