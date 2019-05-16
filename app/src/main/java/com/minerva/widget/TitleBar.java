package com.minerva.widget;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;
import com.minerva.binding.ViewBindings;

public class TitleBar extends LinearLayout {
    private TitleBarViewModel mViewModel;
    private Context context;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public TitleBarViewModel getViewModel() {
        return mViewModel;
    }

    @BindingAdapter("shareClick")
    public static void setShareClick(TitleBar view, ViewBindings.ClickHandler click) {
        view.getViewModel().onShareClick = click;
        view.getViewModel().isShareGone.set(click == null);
    }

    @BindingAdapter("commentClick")
    public static void setCommentClick(TitleBar view, ViewBindings.ClickHandler click) {
        view.getViewModel().onCommentClick = click;
        view.getViewModel().isCommentGone.set(click == null);
    }

    @BindingAdapter("moreClick")
    public static void setMoreClick(TitleBar view, ViewBindings.ClickHandler click) {
        view.getViewModel().onMoreClick = click;
        view.getViewModel().isMoreGone.set(click == null);
    }

    @BindingAdapter("titleBarBg")
    public static void setTitleBarBg(TitleBar view, int color) {
        view.getViewModel().bgColor.set(color);
    }

    private void init() {
        mViewModel = new TitleBarViewModel();
        ViewDataBinding bind = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.title_bar_layout, null, false);
        bind.setVariable(BR.titleBarVM, mViewModel);
        bind.executePendingBindings();
        addView(bind.getRoot());
    }

    public class TitleBarViewModel {
        public ObservableBoolean isShareGone = new ObservableBoolean(true);
        public ObservableBoolean isCommentGone = new ObservableBoolean(true);
        public ObservableBoolean isMoreGone = new ObservableBoolean(true);
        public ObservableInt bgColor = new ObservableInt(R.color.colorPrimaryDark);

        ViewBindings.ClickHandler onShareClick;
        ViewBindings.ClickHandler onCommentClick;
        ViewBindings.ClickHandler onMoreClick;

        public void goBack() {
            ((BaseActivity) context).finish();
        }

        public void share() {
            if (onShareClick != null) {
                onShareClick.onClick();
            }
        }

        public void goComment() {
            if (onCommentClick != null) {
                onCommentClick.onClick();
            }
        }

        public void getMore() {
            if (onMoreClick != null) {
                onMoreClick.onClick();
            }
        }
    }
}
