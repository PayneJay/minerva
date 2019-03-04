package com.minerva.business.search;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseFragment;
import com.minerva.base.BaseViewModel;
import com.minerva.utils.ResouceUtils;

import java.text.MessageFormat;

public class SearchViewModel extends BaseViewModel {
    public static ObservableInt currentItem = new ObservableInt(0);
    public static ObservableField<String> hintText = new ObservableField<>();

    public SearchViewModel(Context context) {
        super(context);
    }

    @BindingAdapter({"searchViewPager"})
    public static void setViewPager(TabLayout view, ViewPager viewPager) {
        viewPager.setAdapter(new SearchFragmentAdapter(((BaseActivity) view.getContext()).getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentItem.set(position);
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
                String format = MessageFormat.format(ResouceUtils.getString(R.string.search_bar_hint), tab.getText().toString());
                hintText.set(format);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
