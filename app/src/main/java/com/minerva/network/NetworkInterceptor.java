package com.minerva.network;

import android.text.TextUtils;
import android.webkit.WebSettings;

import com.minerva.common.Constants;
import com.minerva.common.GlobalData;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkInterceptor implements Interceptor {
    private HashMap<String, String> mHeaders;
    private HashMap<String, String> mParas;
    private boolean isNeedLoginString;

    public NetworkInterceptor(HashMap<String, String> paras, HashMap<String, String> headers, boolean isNeedLoginString) {
        this.mHeaders = headers;
        this.mParas = paras;
        this.isNeedLoginString = isNeedLoginString;
    }

    private static String getUserAgent() {
        String userAgent;
        try {
            userAgent = WebSettings.getDefaultUserAgent(Constants.application);
        } catch (Exception e) {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent == null ? 0 : userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request build = request.newBuilder()
                .headers(getHeaders(request))
                .removeHeader("User-Agent")
                .addHeader("User-Agent", getUserAgent())
                .build();

        if (GlobalData.getInstance().isLogin()) {
            build = request.newBuilder()
                    .headers(getHeaders(request))
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", getUserAgent())
                    .url(addCommonParameter(request))
                    .build();
        }

        return chain.proceed(build);
    }

    private HttpUrl addCommonParameter(Request request) {
        if (isNeedLoginString) {
            HttpUrl.Builder builder = request.url()
                    .newBuilder()
                    .scheme(request.url().scheme())
                    .host(request.url().host());
            return builder.build();
        } else {
            HttpUrl.Builder builder = request.url()
                    .newBuilder()
                    .scheme(request.url().scheme())
                    .host(request.url().host());
            return builder.build();
        }
    }

    private HttpUrl addParameter(Request request) {
        HttpUrl httpUrl = request.url();

        Iterator iter = mParas.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            httpUrl = httpUrl.newBuilder().addQueryParameter(entry.getKey().toString(), entry.getValue().toString()).build();
        }

        return httpUrl;
    }

    private Headers getHeaders(Request request) {
        Headers headers = request.headers();
        Iterator iter = mHeaders.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String> entry = (Map.Entry<String, String>) iter.next();
            String value = entry.getValue();
            headers = headers.newBuilder().add(entry.getKey(), TextUtils.isEmpty(value) ? "" : value).build();
        }
        return headers;
    }
}
