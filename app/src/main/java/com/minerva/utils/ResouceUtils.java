package com.minerva.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.util.DisplayMetrics;
import android.util.Log;

import com.minerva.common.Constants;

public class ResouceUtils {

    /**
     * get color id
     */
    public static int getColor(int id) {
        try {
            return Constants.application.getResources().getColor(id);
        } catch (Exception e) {
            Log.e(Constants.TAG, e.getMessage());
            return 0;
        }
    }

    public static String getString(int id) {
        try {
            return Constants.application.getResources().getString(id);
        } catch (Exception e) {
            Log.e(Constants.TAG, e.getMessage());
            return "";
        }
    }

    /**
     * get drawable id
     */
    public static Drawable getDrawable(int id) {
        try {
            return Constants.application.getResources().getDrawable(id);
        } catch (Exception e) {
            Log.e(Constants.TAG, e.getMessage());
            return null;
        }
    }

    /**
     * get resource name
     */
    public static String getResourceEntryName(@StringRes int id) {
        try {
            return Constants.application.getResources().getResourceEntryName(id);
        } catch (Exception e) {
            Log.e(Constants.TAG, e.getMessage());
            return "";
        }
    }

    /**
     * getDimensionPixelSize
     */
    public static int getDimenPix(int id) {
        try {
            return Constants.application.getResources().getDimensionPixelSize(id);
        } catch (Exception e) {
            Log.e(Constants.TAG, e.getMessage());
            return 0;
        }
    }

    public static float getDimen(int id) {
        try {
            return Constants.application.getResources().getDimension(id);
        } catch (Exception e) {
            Log.e(Constants.TAG, e.getMessage());
            return 0;
        }
    }

}
