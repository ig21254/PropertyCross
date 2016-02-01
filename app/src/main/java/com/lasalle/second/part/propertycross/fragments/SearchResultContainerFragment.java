package com.lasalle.second.part.propertycross.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.adapters.SearchResultTabAdapter;
import com.lasalle.second.part.propertycross.model.PropertySearch;
import com.lasalle.second.part.propertycross.services.ApplicationServiceFactory;

import java.util.ArrayList;

public class SearchResultContainerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_container, container, false);
        createToolbar(view);
        createTabs(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_result_portrait_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search_option_sort_price) {
            if (SearchResultListFragment.SORT_ORDER.equals(SearchResultListFragment.SORT_ORDER_PRICE_ASC)) {
                SearchResultListFragment.SORT_ORDER = SearchResultListFragment.SORT_ORDER_PRICE_DESC;
            } else {
                SearchResultListFragment.SORT_ORDER = SearchResultListFragment.SORT_ORDER_PRICE_ASC;
            }
        } else if (item.getItemId() == R.id.search_option_sort_footage) {
            if (SearchResultListFragment.SORT_ORDER.equals(SearchResultListFragment.SORT_ORDER_FOOTAGE_ASC)) {
                SearchResultListFragment.SORT_ORDER = SearchResultListFragment.SORT_ORDER_FOOTAGE_DESC;
            } else {
                SearchResultListFragment.SORT_ORDER = SearchResultListFragment.SORT_ORDER_FOOTAGE_ASC;
            }
        }

        Intent intent = new Intent(SearchResultListFragment.SORT_INTENT_NAME);
        getActivity().sendBroadcast(intent);

        return super.onOptionsItemSelected(item);
    }

    protected void createTabs(View view) {
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.search_results_frag_tabs);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.search_results_frag_content);

        ArrayList<SearchResultTabAdapter.TabEntry> entryArrayList = new ArrayList<>();
        entryArrayList.add(new SearchResultTabAdapter.TabEntry("All", new SearchResultListFragment(true, true)));
        entryArrayList.add(new SearchResultTabAdapter.TabEntry("Rent", new SearchResultListFragment(true, false)));
        entryArrayList.add(new SearchResultTabAdapter.TabEntry("Sell", new SearchResultListFragment(false, true)));

        SearchResultTabAdapter searchResultTabAdapter = new SearchResultTabAdapter(
                getChildFragmentManager(),
                entryArrayList);

        viewPager.setAdapter(searchResultTabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    protected void createToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.searchResultToolbar);
        AppCompatActivity compatActivity = (AppCompatActivity) getActivity();
        compatActivity.setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
    }
}
