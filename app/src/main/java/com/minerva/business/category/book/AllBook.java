package com.minerva.business.category.book;

import com.minerva.base.BaseBean;
import com.minerva.business.category.model.BookBean;

import java.util.ArrayList;
import java.util.List;

public class AllBook extends BaseBean {
    private boolean has_next;
    private List<BookBean.ItemsBean.BooksBean> items = new ArrayList<>();

    public boolean isHas_next() {
        return has_next;
    }

    public void setHas_next(boolean has_next) {
        this.has_next = has_next;
    }

    public List<BookBean.ItemsBean.BooksBean> getItems() {
        return items;
    }

    public void setItems(List<BookBean.ItemsBean.BooksBean> items) {
        this.items = items;
    }
}
