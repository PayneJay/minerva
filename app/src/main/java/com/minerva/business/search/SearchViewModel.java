package com.minerva.business.search;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;

import com.minerva.R;
import com.minerva.base.BaseActivity;
import com.minerva.base.BaseViewModel;
import com.minerva.business.search.model.SearchModel;
import com.minerva.common.Constants;
import com.minerva.common.EventMsg;
import com.minerva.utils.ResourceUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends BaseViewModel {
    public static ObservableInt currentItem = new ObservableInt(0);
    public static ObservableField<String> hintText = new ObservableField<>();
    public static ObservableField<String> inputContent = new ObservableField<>();
    private List<String> searchHistory = new ArrayList<>();
    public SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            EventBus.getDefault().post(new EventMsg(Constants.EventMsgKey.QUERY_SUBMITTED, query));
            if (!searchHistory.contains(query)) {
                searchHistory.add(query);
            }

            SearchModel.getInstance().setSearchHistory(context, searchHistory);
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
        List<String> history = SearchModel.getInstance().getSearchHistory(context);
        searchHistory.addAll(history);
    }

    @Override
    public void onEvent(EventMsg eventMsg) {
        super.onEvent(eventMsg);
        if (TextUtils.equals(Constants.EventMsgKey.QUERY_SUBMITTED, eventMsg.getMsg())) {
//            inputContent.set(eventMsg.getContent());
        }
    }

    @BindingAdapter({"searchViewPager"})
    public static void setViewPager(TabLayout view, ViewPager viewPager) {
        viewPager.setAdapter(new SearchFragmentAdapter(((BaseActivity) view.getContext()).getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(3);
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
                String format = MessageFormat.format(ResourceUtils.getString(R.string.search_bar_hint), tab.getText() != null ? tab.getText().toString() : "");
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
}
