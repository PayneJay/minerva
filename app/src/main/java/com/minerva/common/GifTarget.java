package com.minerva.common;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

public class GifTarget extends CustomTarget<GifDrawable> {
    private Context context;
    private TextView tv;
    private URLDrawable mDrawable;

    GifTarget(Context context, TextView tv, URLDrawable urlDrawable) {
        this.context = context;
        this.tv = tv;
        this.mDrawable = urlDrawable;
    }

    @Override
    public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
        Rect rect;
        if (resource.getIntrinsicWidth() > 100) {
            float width;
            float height;
            if (resource.getIntrinsicWidth() >= tv.getWidth()) {
                float downScale = (float) resource.getIntrinsicWidth() / tv.getWidth();
                width = (float) resource.getIntrinsicWidth() / downScale;
                height = (float) resource.getIntrinsicHeight() / downScale;
            } else {
                float multiplier = (float) tv.getWidth() / resource.getIntrinsicWidth();
                width = (float) resource.getIntrinsicWidth() * multiplier;
                height = (float) resource.getIntrinsicHeight() * multiplier;
            }
            rect = new Rect(0, 0, Math.round(width), Math.round(height));
        } else {
            rect = new Rect(0, 0, resource.getIntrinsicWidth() * 2, resource.getIntrinsicHeight() * 2);
        }
        resource.setBounds(rect);

        mDrawable.setBounds(rect);
        mDrawable.setDrawable(resource);

        if (resource.isRunning()) {
            mDrawable.setCallback(tv);
            resource.setLoopCount(GifDrawable.LOOP_FOREVER);
            resource.start();
        }

        tv.setText(tv.getText());
        tv.invalidate();
    }

    @Override
    public void onLoadCleared(@Nullable Drawable placeholder) {

    }
}
