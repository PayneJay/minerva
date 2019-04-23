package com.minerva.business.article;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.minerva.business.article.list.ArticleListFragment;
import com.minerva.base.BaseFragment;
import com.minerva.business.article.list.model.ArticleModel;
import com.minerva.business.article.list.model.ArticleType;

import java.util.ArrayList;
import java.util.List;

public class ArticleFragmentAdapter extends FragmentPagerAdapter {
    private final List<ArticleType> tabTypes = new ArrayList<>();
    private ArrayList<BaseFragment> fragments = new ArrayList<>();

    ArticleFragmentAdapter(FragmentManager fm) {
        super(fm);
        tabTypes.clear();
        tabTypes.addAll(ArticleModel.getInstance().getTabTypes());
        for (int i = 0; i < tabTypes.size(); i++) {
            ArticleListFragment fragment = new ArticleListFragment();
            fragment.setIndex(i);
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
        return tabTypes.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTypes.get(position).getTabTitle();
    }

}
