package com.minerva.business.category;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.minerva.base.BaseFragment;
import com.minerva.base.BaseViewModel;
import com.minerva.business.search.SearchActivity;
import com.minerva.business.settings.SettingsActivity;
import com.minerva.common.Constants;

public class CategoryViewModel extends BaseViewModel {
    public static ObservableInt currentItem = new ObservableInt(0);
    public static ObservableBoolean isBookTab = new ObservableBoolean(false);
    public ObservableField<BaseFragment> fragment = new ObservableField<>();

    public CategoryViewModel(Context context) {
        super(context);
    }

    @BindingAdapter({"categoryViewPager", "fragment"})
    public static void setViewPager(TabLayout view, ViewPager viewPager, BaseFragment fragment) {
        viewPager.setAdapter(new CategoryFragmentAdapter(fragment.getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentItem.set(position);
                isBookTab.set((position == 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        view.setupWithViewPager(viewPager, true);
        viewPager.setCurrentItem(currentItem.get());
        view.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void onImgClick() {
        if (isBookTab.get()) {
            Intent intent = new Intent(context, SearchActivity.class);
            intent.putExtra(Constants.KeyExtra.EXTRA_TAB, 2);
            context.startActivity(intent);
            return;
        }

        context.startActivity(new Intent(context, SettingsActivity.class));
    }
}
