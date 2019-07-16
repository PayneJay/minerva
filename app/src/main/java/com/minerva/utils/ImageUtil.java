package com.minerva.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class ImageUtil {
    /**
     * 根据路径获取Bitmap
     *
     * @param path
     * @return
     */
    public static Bitmap getBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inSampleSize = 1; // 图片的大小设置为原来的十分之一
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 保存图片到本地相册
     *
     * @param context
     * @param bmp
     * @return
     */
    public static String saveImageToGallery(Context context, Bitmap bmp) {
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "minerva" + File.separator + "image";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName = DateUtil.date2Str(new Date(System.currentTimeMillis()), "yyyyMMdd_HHmmss") + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return file.getAbsolutePath();
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将URL转化成bitmap形式
     *
     * @param url
     * @return bitmap type
     */
    public static Bitmap returnBitmap(String url) {
        URL myFileUrl;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
