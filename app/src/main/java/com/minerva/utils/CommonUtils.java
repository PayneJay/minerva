package com.minerva.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minerva.business.article.list.model.ArticleBean;
import com.minerva.business.site.model.SitesBean;
import com.minerva.business.category.model.BookBean;
import com.minerva.business.category.model.MagBean;
import com.minerva.common.Constants;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

public class CommonUtils {
    public static SitesBean getSiteListFromJson() {
        Type type = new TypeToken<SitesBean>() {
        }.getType();
        return CommonUtils.getBeanFormAssetJson("sites.json", type);
    }

    public static MagBean getSpecialListFromJson() {
        Type type = new TypeToken<MagBean>() {
        }.getType();
        return CommonUtils.getBeanFormAssetJson("special.json", type);
    }

    public static BookBean getBookListFromJson() {
        Type type = new TypeToken<BookBean>() {
        }.getType();
        return getBeanFormAssetJson("books.json", type);
    }

    public static ArticleBean getArticleListFromJson() {
        Type type = new TypeToken<ArticleBean>() {
        }.getType();
        return getBeanFormAssetJson("articles.json", type);
    }

    private static <T> T getBeanFormAssetJson(String fileName, Type typeOfT) {
        Gson gson = new Gson();

        String reponseStr = "";

        try {
            InputStream inputStream = Constants.application.getApplicationContext().getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            reponseStr = new String(buffer, "utf-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return gson.fromJson(reponseStr, typeOfT);
    }

    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * json串转List
     *
     * @param str json
     * @return
     */
    public static List<String> json2List(String str) {
        Gson gson = new Gson();
        List<String> history = gson.fromJson(str, new TypeToken<List<String>>() {
        }.getType());

        return history;
    }

    /**
     * 网络连接是否可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                return info.getState() == NetworkInfo.State.CONNECTED;
            }
        }
        return false;
    }

    /**
     * 处理代码显示问题.
     *
     * @param chapter 章节内容的html元素。
     */
    public static void handlerPreTag(Element chapter) {
        if (chapter == null) {
            return;
        }

        Elements preElems = chapter.select("pre");
        for (Element elem : preElems) {
            elem.html(elem.html().replaceAll("\n", "<br/>").replaceAll(" ", " "));
        }
    }

    /**
     * @return 屏幕宽度
     * 获取屏幕宽度
     */
    public static int getScreenWidth() {
        DisplayMetrics dm = Constants.application.getResources()
                .getDisplayMetrics();
        return dm.widthPixels;
    }

}
