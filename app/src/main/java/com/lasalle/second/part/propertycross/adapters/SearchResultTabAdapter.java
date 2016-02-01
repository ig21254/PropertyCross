package com.lasalle.second.part.propertycross.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class SearchResultTabAdapter extends FragmentPagerAdapter {

    public static class TabEntry {
        private Fragment fragment;
        private String name;

        public TabEntry(String name, Fragment fragment) {
            this.name = name;
            this.fragment = fragment;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public String getName() {
            return name;
        }
    };

    private List<TabEntry> entryList;

    public SearchResultTabAdapter(FragmentManager fragmentManager, List<TabEntry> entryList) {
        super(fragmentManager);
        this.entryList = entryList;
    }

    @Override
    public Fragment getItem(int position) {
        return entryList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return entryList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return entryList.get(position).getName();
    }
}
