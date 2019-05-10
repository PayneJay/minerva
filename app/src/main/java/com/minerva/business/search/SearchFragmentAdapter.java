package com.minerva.business.search;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.minerva.R;
import com.minerva.base.BaseFragment;
import com.minerva.business.search.model.SearchModel;
import com.minerva.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchFragmentAdapter extends FragmentPagerAdapter {
    private List<String> tabTitles = new ArrayList<>();
    private ArrayList<BaseFragment> fragments = new ArrayList<>();

    SearchFragmentAdapter(FragmentManager fm) {
        super(fm);
        tabTitles.addAll(SearchModel.getInstance().getTabTitle());
        for (int i = 0; i < tabTitles.size(); i++) {
            SearchListFragment fragment = new SearchListFragment();
            fragment.setTab(i);
            fragments.add(fragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return tabTitles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }
}
