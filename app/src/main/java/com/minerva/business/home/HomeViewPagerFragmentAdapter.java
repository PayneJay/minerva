package com.minerva.business.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.minerva.business.article.ArticleFragment;
import com.minerva.business.mine.MyFragment;
import com.minerva.business.category.CategoryFragment;
import com.minerva.business.site.imperfect.SiteFragment;

import java.util.ArrayList;

public class HomeViewPagerFragmentAdapter extends FragmentPagerAdapter {
    private static final int ARTICLE = 0;
    private static final int THEME = 1;
    private static final int SPECIAL = 2;
    private static final int PERSONAL = 3;
    private static final int[] TABS = new int[]{ARTICLE, THEME, SPECIAL, PERSONAL};
    private ArrayList<Fragment> fragments = new ArrayList<>();

    HomeViewPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new ArticleFragment());
        fragments.add(new SiteFragment());
        fragments.add(new CategoryFragment());
        fragments.add(new MyFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return TABS.length;
    }
}
