package com.orionst.mymaterialdesignapp.fragments.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.orionst.mymaterialdesignapp.R;
import com.orionst.mymaterialdesignapp.fragments.ContainerFragment;
import com.orionst.mymaterialdesignapp.fragments.FavoritesListFragment;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {
    //public class CustomFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private String[] titles = new String[2];

    public CustomFragmentPagerAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        titles[0] = context.getResources().getString(R.string.tab_text_1);
        titles[1] = context.getResources().getString(R.string.tab_text_2);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return ContainerFragment.newInstance();
            case 1: return FavoritesListFragment.newInstance();
            default: throw new IllegalArgumentException("Could not create fragment for position " + position);
        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
