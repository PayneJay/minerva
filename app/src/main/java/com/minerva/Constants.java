package com.minerva;

import android.app.Application;

public class Constants {
    public static Application application;

    public static String TAG = "com.minerva";

    public static String HOST = "http://api.tuicool.com";

    public interface RequestMethod {
        String METHOD_GET = "get";
        String METHOD_POST = "post";
        String METHOD_PUT = "put";
        String METHOD_DELETE = "delete";
    }
}
