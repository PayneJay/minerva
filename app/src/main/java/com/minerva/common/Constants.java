package com.minerva.common;

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

    public interface RecyclerItemType {
        int BLANK_TYPE = 0;
        int ARTICLE_COMMON_TYPE = 1;
    }

    public interface KeyExtra {
        String ARTICLE_ID = "article_id";
    }
}
