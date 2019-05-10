package com.minerva.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.minerva.R;

public class HtmlImageGetter implements Html.ImageGetter {
    private Context context;
    private TextView tv;

    public HtmlImageGetter(Context context, TextView tv) {
        this.context = context;
        this.tv = tv;
    }

    @Override
    public Drawable getDrawable(String source) {
        URLDrawable urlDrawable = new URLDrawable();
        Drawable drawable = context.getResources().getDrawable(R.drawable.abs_img_no);
        drawable.setBounds(
                0,
                0,
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        urlDrawable.setDrawable(drawable);
        Glide.with(context)
                .asBitmap()
                .load(source)
                .into(new BitmapTarget(context, tv, urlDrawable));
        return urlDrawable;
    }
}
