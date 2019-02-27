package com.minerva.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;

public class TitleBar extends LinearLayout {
    private TitleViewModel mViewModel;
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

    private void init() {
        mViewModel = new TitleViewModel();
        ViewDataBinding bind = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.title_bar_layout, null, false);
        bind.setVariable(BR.titleBarVM, mViewModel);
        bind.executePendingBindings();
        addView(bind.getRoot());
    }

    public class TitleViewModel {
        public void goBack() {
            ((BaseActivity) context).finish();
        }

        public void share() {
            Toast.makeText(context, "分享，敬请期待……", Toast.LENGTH_SHORT).show();
        }

        public void goComment() {
            Toast.makeText(context, "评论，敬请期待……", Toast.LENGTH_SHORT).show();
        }

        public void getMore() {
            Toast.makeText(context, "更多，敬请期待……", Toast.LENGTH_SHORT).show();
        }
    }
}
