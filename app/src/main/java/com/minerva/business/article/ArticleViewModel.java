package com.minerva.business.article;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.minerva.base.BaseFragment;
import com.minerva.base.BaseViewModel;

public class ArticleViewModel extends BaseViewModel {
    public static ObservableInt currentItem = new ObservableInt(0);
    public ObservableField<BaseFragment> fragment = new ObservableField<>();

    public ArticleViewModel(Context context) {
        super(context);
    }

    @BindingAdapter({"viewPager", "fragment"})
    public static void setViewPager(TabLayout view, ViewPager viewPager, BaseFragment fragment) {
        viewPager.setAdapter(new ArticleFragmentAdapter(fragment.getChildFragmentManager()));
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
