package com.minerva.network;

import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.widget.Toast;

import com.minerva.Constants;
import com.minerva.GlobalData;
import com.minerva.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nayibo on 2017/7/25.
 */

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(Constants.application);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
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

        if (GlobalData.isLogin()) {
            build = request.newBuilder()
                    .headers(getHeaders(request))
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", getUserAgent())
                    .url(addCommonParameter(request))
                    .build();
        }

        Response response = chain.proceed(build);
        Toast.makeText(Constants.application, R.string.network_error, Toast.LENGTH_SHORT).show();
        return response;
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
