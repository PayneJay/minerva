package com.minerva.business.category.book;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.minerva.BR;
import com.minerva.R;
import com.minerva.base.BaseFragment;

public class BookFragment extends BaseFragment<BookViewModel> {
    private BookViewModel bookViewModel;

    @Override
    protected BookViewModel getViewModel() {
        if (bookViewModel == null) {
            bookViewModel = new BookViewModel(getActivity());
        }
        return bookViewModel;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_book_layout;
    }

    @Override
    protected int getVariableId() {
        return BR.bookVM;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bookViewModel.setGirdLayoutManager();
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
