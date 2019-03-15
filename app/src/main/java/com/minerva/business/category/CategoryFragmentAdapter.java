package com.minerva.business.category;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.minerva.R;
import com.minerva.base.BaseFragment;
import com.minerva.business.category.book.BookFragment;
import com.minerva.business.category.mag.SpecialFragment;
import com.minerva.utils.ResouceUtils;

import java.util.ArrayList;

class CategoryFragmentAdapter extends FragmentPagerAdapter {
    private final String[] tabTitles = new String[]{ResouceUtils.getString(R.string.tab_special), ResouceUtils.getString(R.string.tab_book)};
    private ArrayList<BaseFragment> fragments = new ArrayList<>();

    public CategoryFragmentAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new SpecialFragment());
        fragments.add(new BookFragment());
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
