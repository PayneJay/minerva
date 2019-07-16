package com.minerva.common;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class Constants {
    public static Application application;

    public static final String TAG = "***com.minerva***";

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
        int MY_MESSAGE_ITEM_TYPE = 11;   //消息通知item
        int NO_MORE_ITEM_TYPE = 12;   //没有更多item
        int SUBSCRIBE_SITE_CHILD_ITEM_TYPE = 13;   //站点订阅子item
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
        String WEB_URL_LINK = "web_url_link"; //跳转webView链接
        String WEB_VIEW_TITLE = "web_view_title"; //webView标题

        String IMAGE_BROWSE_URL = "image_browse_url"; //浏览大图
        String READ_LATER_MAP = "read_later_map"; //待读文章
        String READ_HISTORY_MAP = "read_history_map"; //阅读历史
        String COME_FROM_MINE = "come_from_mine"; //标记是从我的页面跳转过来的
        String WEEKLY_ID = "weekly_id"; //一周拾遗id
        String WEEKLY_DATE = "weekly_date"; //一周拾遗时间
        String EXTRA_TAB = "extra_tab";
        String FAV_KAN_ID = "fav_kan_id";
        String LAST_LOGIN_EMAIL = "last_login_email";
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
        String SELECT_ARTICLE_LANGUAGE = "select_article_language";
    }

    public interface OauthType {
        int TYPE_EMAIL = 0;
        int TYPE_QQ_WEIBO = 1;
        int TYPE_QQ = 2;
        int TYPE_WEIXIN = 4;
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
