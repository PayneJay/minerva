package com.minerva.business.search;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.search.model.SearchModel;
import com.minerva.common.Constants;
import com.minerva.common.EventMsg;
import com.minerva.common.TabLayoutHelper;
import com.minerva.utils.ResourceUtil;

import org.greenrobot.eventbus.EventBus;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends BaseViewModel {
    public static ObservableInt currentItem = new ObservableInt(0);
    public static ObservableField<String> hintText = new ObservableField<>();
    public static ObservableField<String> inputContent = new ObservableField<>();
    public SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            EventBus.getDefault().post(new EventMsg(Constants.EventMsgKey.QUERY_SUBMITTED, query));
            SearchModel.getInstance().setSearchHistory(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    SearchViewModel(Context context) {
        super(context);
        EventBus.getDefault().register(this);
        gotoTab(((BaseActivity) context).getIntent());
    }

    @Override
    public void onEvent(EventMsg eventMsg) {
        super.onEvent(eventMsg);
        if (TextUtils.equals(Constants.EventMsgKey.QUERY_ECHO, eventMsg.getMsg())) {
            inputContent.set(eventMsg.getContent());
        }
    }

    @BindingAdapter({"searchViewPager"})
    public static void setViewPager(final TabLayout tabLayout, ViewPager viewPager) {
        viewPager.setAdapter(new SearchFragmentAdapter(((BaseActivity) tabLayout.getContext()).getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentItem.set(position);
                TabLayout.Tab tab = tabLayout.getTabAt(position);
                if (tab != null) {
                    tab.select();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setupWithViewPager(viewPager, true);
        viewPager.setCurrentItem(currentItem.get());
        new TabLayoutHelper.Builder(tabLayout)
                .setIndicatorColor(Color.WHITE)
                .setIndicatorHeight(8)
                .setIndicatorWith(150)
                .setIndicatorDrawable(R.drawable.shape_tab_line)
                .setSelectedTextColor(Color.WHITE)
                .setNormalTextColor(ResourceUtil.getColor(R.color.color_CFFFFFFF))
                .setSelectedTextSize(16)
                .setNormalTextSize(14)
                .setSelectedBold(true)
                .setNormalBackgroundColor(ResourceUtil.getColor(R.color.colorPrimaryDark))
                .setSelectedBackgroundColor(ResourceUtil.getColor(R.color.colorPrimaryDark))
                .setCurrentTab(currentItem.get())
                .setTabItemWith(200)
                .build();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String format = MessageFormat.format(ResourceUtil.getString(R.string.search_bar_hint), tab.getText() != null ? tab.getText().toString() : "");
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

    @BindingAdapter(value = {"queryTextListener", "queryText"}, requireAll = false)
    public static void setOnQueryTextListener(SearchView searchView, SearchView.OnQueryTextListener listener, String queryText) {
        if (listener != null) {
            searchView.setOnQueryTextListener(listener);
            searchView.setQuery(queryText, true);
        }
    }

    private void gotoTab(Intent intent) {
        int tab = intent.getIntExtra(Constants.KeyExtra.EXTRA_TAB, 0);
        hintText.set(MessageFormat.format(ResourceUtil.getString(R.string.search_bar_hint), SearchModel.getInstance().getTabTitle().get(tab)));
        currentItem.set(tab);
    }
}
