package com.minerva.business.article;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.minerva.R;
import com.minerva.base.BaseFragment;
import com.minerva.base.BaseViewModel;
import com.minerva.common.TabLayoutHelper;
import com.minerva.utils.ResourceUtils;

public class ArticleViewModel extends BaseViewModel {
    public static ObservableInt currentItem = new ObservableInt(0);
    public ObservableField<BaseFragment> fragment = new ObservableField<>();

    ArticleViewModel(Context context) {
        super(context);
    }

    @BindingAdapter({"viewPager", "fragment"})
    public static void setViewPager(TabLayout tabLayout, ViewPager viewPager, BaseFragment fragment) {
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

        viewPager.setCurrentItem(currentItem.get());
        tabLayout.setupWithViewPager(viewPager, true);
        new TabLayoutHelper.Builder(tabLayout)
                .setIndicatorColor(Color.WHITE)
                .setIndicatorHeight(8)
                .setIndicatorWith(150)
                .setIndicatorDrawable(R.drawable.shape_tab_line)
                .setSelectedTextColor(Color.WHITE)
                .setNormalTextColor(ResourceUtils.getColor(R.color.color_CFFFFFFF))
                .setSelectedTextSize(16)
                .setNormalTextSize(14)
                .setSelectedBold(true)
                .setNormalBackgroundColor(ResourceUtils.getColor(R.color.colorPrimaryDark))
                .setSelectedBackgroundColor(ResourceUtils.getColor(R.color.colorPrimaryDark))
                .setCurrentTab(currentItem.get())
                .setTabItemWith(200)
                .build();
    }
}
