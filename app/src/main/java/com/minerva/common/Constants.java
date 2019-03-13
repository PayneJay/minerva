package com.minerva.common;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class Constants {
    public static Application application;

    public static final String TAG = "com.minerva";

    public static final String HOST = "http://api.tuicool.com";

    public static void showToast(Context context) {
        Toast.makeText(context, "攻城狮正在奋力开发中，尽情期待……", Toast.LENGTH_SHORT).show();
    }

    public interface RequestMethod {
        String METHOD_GET = "get";
        String METHOD_POST = "post";
        String METHOD_PUT = "put";
        String METHOD_DELETE = "delete";
    }

    public interface RecyclerItemType {
        int BLANK_TYPE = 0; //空白item
        int ARTICLE_COMMON_TYPE = 1;    //文章item
        int SPECIAL_GROUP_TYPE = 2;    //专栏group item
        int SPECIAL_CHILD_TYPE = 3;    //专栏child item
        int PERIODICAL_TYPE = 4;        //期刊头部item
    }

    public interface KeyExtra {
        String ARTICLE_ID = "article_id";
        String PERIODICAL_ID = "periodical_id";
        String PERIODICAL_IMAGE = "periodical_image";
        String PERIODICAL_NAME = "periodical_name";
    }

    public interface LoginInfo {
        String USER_NAME = "Minerva@gmail.com";
        String PASSWORD = "123456";
        String IS_LOGIN = "minerva_is_login";
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
