package com.minerva.network;

import android.util.Log;

import com.minerva.common.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.i(Constants.TAG, "request: " + request.url().toString());
        return chain.proceed(request);
    }
}
