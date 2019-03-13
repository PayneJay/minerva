package com.minerva.business.category.book;

import com.minerva.R;
import com.minerva.BR;
import com.minerva.base.BaseActivity;

public class BookDetailActivity extends BaseActivity<BookDetailViewModel> {
    @Override
    protected int getLayoutResID() {
        return R.layout.activity_book_detail_layout;
    }

    @Override
    protected BookDetailViewModel getViewModel() {
        return new BookDetailViewModel(this);
    }

    @Override
    protected int getVariableID() {
        return BR.bookDetailVM;
    }
}
