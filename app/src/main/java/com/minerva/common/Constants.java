package com.minerva.common;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class Constants {
    public static Application application;

    public static final String TAG = "com.minerva";

    public static final String HOST = "http://api.tuicool.com";

    public static final String shareBaseUrl = "http://www.tuicool.com/articles/";

    public static void showToast(Context context) {
        Toast.makeText(context, "攻城狮正在奋力开发中，尽情期待……", Toast.LENGTH_SHORT).show();
    }

    public interface RequestMethod {
        String METHOD_GET = "get";
        String METHOD_POST = "post";
        String METHOD_PUT = "put";
        String METHOD_DELETE = "delete";
    }

    public interface CategoryTabType {
        String TAB_MAG = "tab_mag";
        String TAB_BOOK = "tab_book";
    }

    public interface RecyclerItemType {
        int BLANK_TYPE = 0; //空白item
        int ARTICLE_COMMON_TYPE = 1;    //文章item
        int SPECIAL_GROUP_TYPE = 2;    //专栏group item
        int SPECIAL_CHILD_TYPE = 3;    //专栏child item
        int PERIODICAL_TITLE_TYPE = 4;       //期刊头部item
        int MAG_CHILD_TYPE = 5;     //期刊详情列表item
        int MAG_TITLE_TYPE = 6;     //期刊详情头部item
        int SEARCH_SITE_TYPE = 7;   //搜索站点列表item
        int SEARCH_BOOK_TYPE = 8;   //搜索图书列表item
        int SEARCH_KW_HISTORY_TYPE = 9;   //搜索关键字列表item
        int MY_JOURNAL_ITEM_TYPE = 10;   //我的期刊item
    }

    public interface KeyExtra {
        String ARTICLE_ID = "article_id";
        String PERIODICAL_ID = "periodical_id";
        String POLYMER_ID = "polymer_id";
        String PERIODICAL_IMAGE = "periodical_image";
        String PERIODICAL_NAME = "periodical_name";
        String COLUMN_MAG_ID = "column_mag_id";
        String COLUMN_MAG_NUMBER = "column_mag_number";
        String COLUMN_MAG_TITLE = "column_mag_title";
        String COLUMN_MAG_TYPE = "column_mag_type";
        String BOOK_JD_LINK = "book_jd_link"; //图书京东商城链接

        String SEARCH_HISTORY_KEYWORD = "search_history_keyword"; //关键字查询
        String READ_LATER_MAP = "read_later_map"; //待读文章
        String READ_HISTORY_MAP = "read_history_map"; //阅读历史
        String COME_FROM_MINE = "come_from_mine"; //标记是从我的页面跳转过来的
    }

    public interface UserInfoKey {
        String USER_ID = "minerva_user_id";
        String USER_TOKEN = "minerva_user_token";

        //以下用户信息相关
        String USER_PROFILE = "minerva_user_profile";
        String USER_NAME = "minerva_user_name";
        String USER_EMAIL = "minerva_user_email";
        String PASS_WORD = "minerva_pass_word";
        String WEIBO = "minerva_weibo";
        String QQ = "minerva_qq";
        String WECHAT = "minerva_wechat";
    }

    public interface BitmapTransform {
        int CIRCLE = 100;//圆形图片

        int ROUNDED = 200;//圆角图片

    }

    public interface PageStatus {
        int NO_DATA = 100;    //无数据

        int NETWORK_EXCEPTION = 200;//网络异常

    }

    public interface EventMsgKey {
        String LOGIN_SUCCESS = "login_success";
        String QUERY_SUBMITTED = "query_submitted";
        String QUERY_ECHO = "query_echo";
        String DELETE_SEARCH_KEYWORD = "delete_search_keyword";
        String CANCEL_FAVORITE_ARTICLE = "cancel_favorite_article";
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
