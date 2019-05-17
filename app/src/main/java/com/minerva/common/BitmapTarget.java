package com.minerva.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class BitmapTarget extends CustomTarget<Bitmap> {
    private Context context;
    private TextView tv;
    private URLDrawable urlDrawable;

    BitmapTarget(Context context, TextView textView, URLDrawable urlDrawable) {
        this.context = context;
        this.tv = textView;
        this.urlDrawable = urlDrawable;

    }

    @Override
    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
        Drawable drawable = new BitmapDrawable(context.getResources(), resource);
        Rect rect;
        if (drawable.getIntrinsicWidth() > 100) {
            float width;
            float height;
            if (drawable.getIntrinsicWidth() >= tv.getWidth()) {
                float downScale = (float) drawable.getIntrinsicWidth() / tv.getWidth();
                width = (float) drawable.getIntrinsicWidth() / downScale;
                height = (float) drawable.getIntrinsicHeight() / downScale;
            } else {
                float multiplier = (float) tv.getWidth() / drawable.getIntrinsicWidth();
                width = (float) drawable.getIntrinsicWidth() * multiplier;
                height = (float) drawable.getIntrinsicHeight() * multiplier;
            }
            rect = new Rect(0, 0, Math.round(width), Math.round(height));
        } else {
            rect = new Rect(0, 0, drawable.getIntrinsicWidth() * 2, drawable.getIntrinsicHeight() * 2);
        }
        drawable.setBounds(rect);

        urlDrawable.setBounds(rect);
        urlDrawable.setDrawable(drawable);
        tv.setText(tv.getText());
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.invalidate();
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {

    }

}
