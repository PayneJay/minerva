package com.minerva.utils;

import android.widget.Toast;

import com.minerva.common.Constants;

public class ToastUtil {
    public static void showMsg(String text) {
        Toast.makeText(Constants.application, text, Toast.LENGTH_SHORT).show();
    }
}
