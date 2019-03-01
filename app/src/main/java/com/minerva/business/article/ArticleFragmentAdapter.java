package com.minerva.business.article;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.minerva.R;
import com.minerva.business.article.list.ArticleListFragment;
import com.minerva.base.BaseFragment;
import com.minerva.utils.ResouceUtils;

import java.util.ArrayList;

public class ArticleFragmentAdapter extends FragmentPagerAdapter {
    private final String[] tabTitles = new String[]{ResouceUtils.getString(R.string.article_hot), ResouceUtils.getString(R.string.article_recommend),
            ResouceUtils.getString(R.string.article_technology), ResouceUtils.getString(R.string.article_start_up),
            ResouceUtils.getString(R.string.article_numercial), ResouceUtils.getString(R.string.article_design), ResouceUtils.getString(R.string.article_marketing)};
    private ArrayList<BaseFragment> fragments = new ArrayList<>();

    public ArticleFragmentAdapter(FragmentManager fm) {
        super(fm);
        for (int i = 0; i < tabTitles.length; i++) {
            ArticleListFragment fragment = new ArticleListFragment();
            fragment.setRecTab(i == 1);
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
