package com.minerva.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.minerva.utils.CommonUtils;

public class BitmapTarget extends CustomTarget<Bitmap> {
    private Context context;
    private TextView tv;
    private URLDrawable urlDrawable;
    private int imgWidth, imgHeight;

    BitmapTarget(Context context, TextView textView, URLDrawable urlDrawable) {
        this.context = context;
        this.tv = textView;
        this.urlDrawable = urlDrawable;

        imgWidth = CommonUtils.getScreenWidth();
        imgHeight = 9 * imgWidth / 16;
    }

    @Override
    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
        Drawable drawable = new BitmapDrawable(context.getResources(), resource);
        //自定义drawable的高宽, 缩放图片大小最好用matrix变化，可以保证图片不失真
        drawable.setBounds(0, 0, imgWidth, imgHeight);
        urlDrawable.setBounds(0, 0, imgWidth, imgHeight);
        urlDrawable.setDrawable(drawable);
        tv.setText(tv.getText());
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.invalidate();
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {

    }

    /**
     * 对Drawable进行强制缩放
     *
     * @param drawable
     * @param w
     * @param h
     * @param dpi
     * @return
     */
    public static Drawable zoomDrawable(Drawable drawable, int w, int h, float dpi) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable);
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth * dpi, scaleHeight * dpi);
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(null, newbmp);
    }

    private static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }
}
