package com.mindmade.mcom.adapterclasses;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mindmade technologies on 5/6/2017.
 */
public class TabPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mComlist;
    private List<String> mFragmentTitleList = new ArrayList<>();
    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
    public TabPagerAdapter(FragmentManager fm, ArrayList<Fragment> frag, ArrayList<String> title) {
        super(fm);
        mComlist=frag;
        mFragmentTitleList=title;
    }

    @Override
    public Fragment getItem(int position) {
        return mComlist.get(position);//returns item at the specified position
    }

    @Override
    public int getCount() {
        return mComlist.size();//returns total number of items in the list
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position); //returns page title according to its position
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(Object object) {
        // POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
    }
}
