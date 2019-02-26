package com.minerva.binding;

import android.databinding.BindingAdapter;
import android.support.v4.view.ViewPager;

import java.util.List;

import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import me.tatarka.bindingcollectionadapter2.OnItemBind;

public class ViewPagerBindings {
    private ViewPagerBindings() {
        throw new AssertionError("No instances.");
    }

    @BindingAdapter(value = {"itemBinding", "items", "adapter", "onItemBound"}, requireAll = false)
    public static <T> void setAdapter(ViewPager viewPager,
                                      OnItemBind<T> onItemBind,
                                      List<T> items,
                                      BindingViewPagerAdapter<T> adapter,
                                      OnItemBoundHandler<T> onItemBoundHandler) {

        ItemBinding<T> itemBinding = ((onItemBind != null) && (onItemBoundHandler != null))
                ? ItemBinding.of(new OnItemBindWrapper<>(onItemBind, onItemBoundHandler))
                : ItemBinding.of(onItemBind);
        BindingViewPagerAdapters.setAdapter(viewPager, itemBinding, items, adapter);
    }

    @BindingAdapter(value = {"itemBinding", "items", "adapter", "onItemBound", "goPosition"}, requireAll = false)
    public static <T> void setAdapter(ViewPager viewPager,
                                      OnItemBind<T> onItemBind,
                                      List<T> items,
                                      BindingViewPagerAdapter<T> adapter,
                                      OnItemBoundHandler<T> onItemBoundHandler,
                                      int position) {

        ItemBinding<T> itemBinding = ((onItemBind != null) && (onItemBoundHandler != null))
                ? ItemBinding.of(new OnItemBindWrapper<>(onItemBind, onItemBoundHandler))
                : ItemBinding.of(onItemBind);
        BindingViewPagerAdapters.setAdapter(viewPager, itemBinding, items, adapter);
        goPosition(viewPager, position);
    }

    @BindingAdapter("goPosition")
    public static void goPosition(ViewPager view, int position) {
        if (view.getAdapter() == null) {
            return;
        }

        if (view.getAdapter().getCount() > position) {
            view.setCurrentItem(position);
        }
    }

    @BindingAdapter("pageChangeListener")
    public static void pageChangeListener(ViewPager view, final PageChangeHandler handler) {
        if (view != null) {
            view.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    handler.handler(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    @BindingAdapter("limitOffset")
    public static void setLimitOffset(ViewPager view, int offset) {
        view.setOffscreenPageLimit(offset);
    }

    public interface OnItemBoundHandler<T> {
        void onItemBound(int position);
    }

    public interface PageChangeHandler {
        void handler(int position);
    }

    private static class OnItemBindWrapper<T> implements OnItemBind<T> {

        private final OnItemBind<T> onItemBind;
        private final OnItemBoundHandler<T> onItemBoundHandler;

        public OnItemBindWrapper(OnItemBind<T> onItemBind, OnItemBoundHandler<T> onItemBoundHandler) {
            this.onItemBind = onItemBind;
            this.onItemBoundHandler = onItemBoundHandler;
        }

        @Override
        public void onItemBind(ItemBinding itemBinding, int position, T item) {
            onItemBind.onItemBind(itemBinding, position, item);
            onItemBoundHandler.onItemBound(position);
        }

    }
}
