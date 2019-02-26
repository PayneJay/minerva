package com.minerva.binding;

import android.databinding.BindingAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

public class BindingViewPagerAdapters {
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"itemBinding", "items", "adapter"}, requireAll = false)
    public static <T> void setAdapter(ViewPager viewPager,
                                      ItemBinding<T> itemBinding,
                                      List<T> items,
                                      BindingViewPagerAdapter<T> adapter) {
        if (itemBinding == null) {
            throw new IllegalArgumentException("itemBinding must not be null");
        }

        BindingViewPagerAdapter oldAdapter = (BindingViewPagerAdapter) viewPager.getAdapter();
        if (adapter == null) {
            if (oldAdapter == null) {
                adapter = new BindingViewPagerAdapter<>();
            } else {
                adapter = oldAdapter;
            }
        }

        adapter.setItemBinding(itemBinding);
        adapter.setItems(items);

        if (oldAdapter != adapter) {
            viewPager.setAdapter(adapter);
        }
    }
}
