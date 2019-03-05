package com.minerva.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minerva.base.BaseBean;
import com.minerva.business.site.model.SitesBean;
import com.minerva.business.special.model.BookBean;
import com.minerva.business.special.model.SpecialBean;
import com.minerva.common.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

public class CommonUtils {
    public static SitesBean getSiteListFromJson() {
        Type type = new TypeToken<SitesBean>() {
        }.getType();
        return CommonUtils.getBeanFormAssetJson("sites.json", type);
    }

    public static SpecialBean getSpecialListFromJson() {
        Type type = new TypeToken<SpecialBean>() {
        }.getType();
        return CommonUtils.getBeanFormAssetJson("special.json", type);
    }

    public static BookBean getBookListFromJson() {
        Type type = new TypeToken<BookBean>() {
        }.getType();
        return getBeanFormAssetJson("books.json", type);
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
}
