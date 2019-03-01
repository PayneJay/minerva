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

    public interface LoginInfo {
        String USER_NAME = "Minerva@gmail.com";
        String PASSWORD = "123456";
    }

    public interface Activity {
        /**
         * get action through root filter key
         */
        String ROOT_FILTER = application.getPackageName()
                + "_root_filter";
        /**
         * get update infos through root update key
         */
        String ROOT_UPDATE = "root_update";
        /**
         * get update app bean's key
         */
        String ROOT_GOTO_UPDATE_APP = "goto_update_app";
        /**
         * close app
         */
        int ROOT_CLOSE_APP = 1;
    }
}
