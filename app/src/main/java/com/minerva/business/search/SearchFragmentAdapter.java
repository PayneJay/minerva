package com.minerva.business.search;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.minerva.R;
import com.minerva.base.BaseFragment;
import com.minerva.business.article.list.ArticleListFragment;
import com.minerva.utils.ResouceUtils;

import java.util.ArrayList;

public class SearchFragmentAdapter extends FragmentPagerAdapter {
    private final String[] tabTitles = new String[]{ResouceUtils.getString(R.string.tab_article), ResouceUtils.getString(R.string.tab_theme),
            ResouceUtils.getString(R.string.tab_special)};
    private ArrayList<BaseFragment> fragments = new ArrayList<>();

    public SearchFragmentAdapter(FragmentManager fm) {
        super(fm);
        for (int i = 0; i < tabTitles.length; i++) {
            SearchListFragment fragment = new SearchListFragment();
            fragments.add(fragment);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
