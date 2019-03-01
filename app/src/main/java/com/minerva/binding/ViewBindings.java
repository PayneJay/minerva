package com.minerva.binding;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.MovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;


/**
 * Created by nayibo on 2018/4/2.
 */

public class ViewBindings {

    private ViewBindings() {
        throw new AssertionError("No instances.");
    }

    @BindingAdapter("onClick")
    public static void onClick(View view, final ClickHandler clickHandler) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickHandler != null) {
                    clickHandler.onClick();
                }
            }
        });
    }

    @BindingAdapter("isGone")
    public static void isGone(View view, Boolean isGone) {
        view.setVisibility(isGone ? View.GONE : View.VISIBLE);
    }

    @BindingAdapter("isInvisible")
    public static void isInvisible(View view, Boolean isInvisible) {
        view.setVisibility(isInvisible ? View.INVISIBLE : View.VISIBLE);
    }

    @BindingAdapter("android:imageSrc")
    public static void setImageSrc(ImageView imageView, int resId) {
        if (resId != 0) {
            imageView.setImageResource(resId);
        }
    }

    @BindingAdapter("android:drawableRight")
    public static void setRightDrawable(TextView view, int resID) {
        if (resID != 0) {
            Drawable drawable = view.getContext().getResources().getDrawable(resID);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            view.setCompoundDrawables(null, null, drawable, null);
        }
    }

    @BindingAdapter("android:drawableLeft")
    public static void setLeftDrawable(TextView view, int resID) {
        if (resID != 0) {
            Drawable drawable = view.getContext().getResources().getDrawable(resID);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            view.setCompoundDrawables(drawable, null, null, null);
        }
    }

    @BindingAdapter("isEnable")
    public static void isEnable(View view, Boolean isEnable) {
        view.setEnabled(isEnable);
    }

    @BindingAdapter({"imageUrl", "placeHolder", "error"})
    public static void loadImage(ImageView imageView, String url, Drawable holderDrawable, Drawable errorDrawable) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(holderDrawable)
                .error(errorDrawable)
                .centerCrop()
                .into(imageView);
    }

    @BindingAdapter({"imageUrlNoCache", "placeHolderNoCache", "errorNoCache"})
    public static void loadImageNoCache(ImageView imageView, String url, Drawable holderDrawable, Drawable errorDrawable) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(holderDrawable)
                .error(errorDrawable)
                .skipMemoryCache(true)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(imageView);
    }


    /**
     * 只适用 fitCenter 类型的图片
     *
     * @param imageView
     * @param url
     * @param drawableRes
     */
    @BindingAdapter(value = {"url", "place"}, requireAll = false)
    public static void loadImageNotRequireAll(ImageView imageView, String url, @DrawableRes int drawableRes) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(drawableRes)
                .fitCenter()
                .skipMemoryCache(false)
                .dontAnimate()
                .into(imageView);
    }

    @BindingAdapter(value = {"urlThumbnail", "placeThumbnail"}, requireAll = false)
    public static void loadImageNotRequireAllWithThumbnal(ImageView imageView, String url, @DrawableRes int drawableRes) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(drawableRes)
                .fitCenter()
                .skipMemoryCache(false)
                .dontAnimate()
                .thumbnail(0.1f)
                .into(imageView);
    }

    @BindingAdapter({"imageUrlThumbnail", "placeHolderThumbnail", "errorThumbnail"})
    public static void loadImageWithThumbnal(final ImageView imageView, String url, Drawable holderDrawable, Drawable errorDrawable) {
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(holderDrawable)
                .error(errorDrawable)
                .centerCrop()
                .thumbnail(0.1f)
                .dontAnimate()
                .into(imageView);
    }

    /**
     * @param imageView
     * @param url         判断url是否为网址，else为本地图片
     * @param drawableRes
     */
    @BindingAdapter(value = {"urlOrPathCenterCrop", "placeCenterCrop"}, requireAll = false)
    public static void loadImageByUrlPathCenterCrop(ImageView imageView, String url, @DrawableRes int drawableRes) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (url.startsWith("http")) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .placeholder(drawableRes)
                    .centerCrop()
                    .thumbnail(0.1f)
                    .dontAnimate()
                    .into(imageView);
        } else {
            Glide.with(imageView.getContext()).load(new File(url))
                    .centerCrop()
                    .thumbnail(0.1f)
                    .placeholder(drawableRes)
                    .into(imageView);
        }
    }

    @BindingAdapter("loadMore")
    public static void recyclerLoadMore(RecyclerView view, final ClickHandler handler) {
        if (view == null) {
            return;
        }

        view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset()
                        >= recyclerView.computeVerticalScrollRange()) {
                    handler.onClick();
                }
            }
        });
    }


    @BindingAdapter("onLongClick")
    public static void setOnItemLongClick(View view, final OnLongClickListener listener) {
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (listener != null) {
                    listener.onItemLongClick();
                    return true;
                }
                return false;
            }
        });
    }

    @BindingAdapter("text")
    public static void setText(TextView textView, String text) {
        textView.setText(text);
    }

    @BindingAdapter("color")
    public static void setTextColor(TextView textView, int colorResId) {
        textView.setTextColor(ContextCompat.getColor(textView.getContext(), colorResId));
    }

    @BindingAdapter("courseSelection")
    public static void setSelection(EditText editText, int position) {
        editText.setSelection(position);
    }

    @BindingAdapter("hint")
    public static void setHint(TextView textView, String text) {
        textView.setHint(text);
    }

    @BindingAdapter("hintColor")
    public static void setHintColor(EditText view, int color) {
        view.setHintTextColor(color);
    }

    @BindingAdapter("textWatcher")
    public static void addOnEditTextChanged(EditText editText, TextWatcher listener) {
        if (listener != null) {
            editText.addTextChangedListener(listener);
        }
    }

//    @BindingAdapter("showSoftInput")
//    public static void showSoftInput(EditText view, boolean flag) {
//        if (flag) {
//            view.requestFocus();
//            if (!TextUtils.isEmpty(view.getText())) {
//                view.setSelection(view.getText().length());
//            }
//            ScreenUtils.showSoftKeyboard();
//        } else {
//            ScreenUtils.hideSoftKeyboard(CommonUtils.getActivityFromView(view));
//        }
//    }

    @BindingAdapter("showPassword")
    public static void showPassword(EditText view, boolean flag) {
        if (flag) {
            view.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            view.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
        view.setSelection(view.getText().toString().length());
    }

    @BindingAdapter("clearRadioGroupState")
    public static void clearRadioGroupChildState(RadioGroup view, boolean flag) {
        if (flag) {
            view.clearCheck();
        }
    }

    @BindingAdapter("scrollToPosition")
    public static void scrollToPosition(RecyclerView recyclerView, int position) {
        if (position != -1) {
            recyclerView.smoothScrollToPosition(position);
        }
    }

    @BindingAdapter({"android:src"})
    public static void setSrc(ImageView view, int resId) {
        view.setImageResource(resId);
    }


    @BindingAdapter("colorSchemeColors")
    public static void setColorSchemeColors(SwipeRefreshLayout refreshLayout, int[] colors) {
        if (refreshLayout != null) {
            refreshLayout.setColorSchemeColors(colors);
        }
    }

    @BindingAdapter(value = {"itemTouchHelper"})
    public static void dragPhotoItem(RecyclerView recyclerView, ItemTouchHelper itemTouchHelper) {
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @BindingAdapter(value = {"bg"})
    public static void setTextViewBg(TextView view, int res) {
        view.setBackgroundResource(res);
    }

    @BindingAdapter(value = {"scrollToPositionWithOffset"})
    public static void scrollToPositionWithOffset(RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {
        int position = linearLayoutManager.findFirstVisibleItemPosition();
        View view = recyclerView.getChildAt(position);
        if (view != null) {
            int top = view.getTop();
            linearLayoutManager.scrollToPositionWithOffset(linearLayoutManager.getChildCount() - 1, top);
        }
    }

    @BindingAdapter(value = "gotoItem")
    public static void gotoItemViewPager(ViewPager view, int position) {
        if (view != null && view.getAdapter() != null && view.getAdapter().getCount() > position) {
            if (view.getCurrentItem() != position) {
                view.setCurrentItem(position);
            }
        }
    }

    @BindingAdapter("movementMethod")
    public static void setMovementMethod(TextView view, MovementMethod method) {
        view.setMovementMethod(method);
    }

    @BindingAdapter("input_filters")
    public static void setInputFilter(EditText view, InputFilter[] filters) {
        if (filters != null && filters.length > 0) {
            view.setFilters(filters);
        }
    }

    @BindingAdapter("input_type")
    public static void setInputType(EditText editText, int type) {
        if (type == (InputType.TYPE_CLASS_NUMBER)) {
            String dig = "0123456789";
            editText.setKeyListener(DigitsKeyListener.getInstance(dig));
        }
        editText.setInputType(type);
    }

    @BindingAdapter("textSize")
    public static void setTextSize(TextView textView, int dimenSize) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, dimenSize);
    }

//    @BindingAdapter("textWatcher")
//    public static void setTextWatcher(EditText editText,TextWatcher textWatcher){
//        editText.addTextChangedListener(textWatcher);
//    }

    @BindingAdapter("enabled")
    public static void setClickable(TextView textView, boolean enabled) {
        textView.setEnabled(enabled);
    }


    public interface ClickHandler {
        void onClick();
    }

    public interface SeekBarChange {
        void onChange(int volume);
    }

    public interface OnLongClickListener {
        void onItemLongClick();
    }

    public interface OnClickListener {
        void onItemClick();
    }

    public interface StartTouchListener {
        void start(int position);
    }

    public interface OnCheckHandler {
        void onCheck(Object Id);
    }

}
