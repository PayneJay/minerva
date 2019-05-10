package com.minerva.widget;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseViewModel;

public class SearchBar extends LinearLayout {
    private Context context;
    private SearchBarViewModel mViewModel;

    public SearchBar(Context context) {
        this(context, null);
    }

    public SearchBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        mViewModel = new SearchBarViewModel(context);
        ViewDataBinding bind = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.search_bar_layout, null, false);
        bind.setVariable(BR.searchBarVM, mViewModel);
        bind.executePendingBindings();
        addView(bind.getRoot());
    }

    public class SearchBarViewModel extends BaseViewModel {
        public SearchBarViewModel(Context context) {
            super(context);
        }
    }
}
