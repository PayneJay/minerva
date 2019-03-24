package com.minerva.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.minerva.utils.CommonUtils;
import com.minerva.utils.ResouceUtils;

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
}
