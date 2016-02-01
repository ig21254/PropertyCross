package com.lasalle.second.part.propertycross.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.adapters.SearchResultListAdapter;
import com.lasalle.second.part.propertycross.model.Comparators.PropertyFootageAscComparator;
import com.lasalle.second.part.propertycross.model.Comparators.PropertyFootageDescComparator;
import com.lasalle.second.part.propertycross.model.Comparators.PropertyPriceAscComparator;
import com.lasalle.second.part.propertycross.model.Comparators.PropertyPriceDescComparator;
import com.lasalle.second.part.propertycross.model.Property;
import com.lasalle.second.part.propertycross.model.PropertySearch;
import com.lasalle.second.part.propertycross.services.ApplicationServiceFactory;
import com.lasalle.second.part.propertycross.services.PropertyService;

import java.util.Collections;
import java.util.List;

public class SearchResultListFragment extends Fragment {
    public static String SORT_INTENT_NAME   = "com.lasalle.propertycross.SORT_CHANGED";
    public static String SORT_ORDER = "NONE";

    public static final String SORT_ORDER_PRICE_ASC  = "PRICE_ASC";
    public static final String SORT_ORDER_PRICE_DESC = "PRICE_DESC";
    public static final String SORT_ORDER_FOOTAGE_ASC = "FOOTAGE_ASC";
    public static final String SORT_ORDER_FOOTAGE_DESC = "FOOTAGE_DESC";

    private BroadcastReceiver broadcastReceiver;
    private boolean rent;
    private boolean sell;
    private List<Property> results;

    private SearchResultListAdapter searchResultListAdapter;


    public SearchResultListFragment() {
    }

    public SearchResultListFragment(boolean rent, boolean sell) {
        this.rent = rent;
        this.sell = sell;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerBroadcastReceiver();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    protected void registerBroadcastReceiver() {
        IntentFilter filter = new IntentFilter(SORT_INTENT_NAME);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (SORT_ORDER) {
                    case SORT_ORDER_PRICE_ASC:
                        Collections.sort(results, new PropertyPriceAscComparator());
                        break;
                    case SORT_ORDER_PRICE_DESC:
                        Collections.sort(results, new PropertyPriceDescComparator());
                        break;
                    case SORT_ORDER_FOOTAGE_ASC:
                        Collections.sort(results, new PropertyFootageAscComparator());
                        break;
                    case SORT_ORDER_FOOTAGE_DESC:
                        Collections.sort(results, new PropertyFootageDescComparator());
                        break;
                }
                searchResultListAdapter.notifyDataSetChanged();
            }
        };
        getActivity().registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        PropertyService propertyService = ApplicationServiceFactory.getInstance(getActivity())
                .getPropertyService();
        PropertySearch lastSearch = propertyService.getLastSearch();
        results = lastSearch.getFilteredResults(rent, sell);

        searchResultListAdapter = new SearchResultListAdapter(
                getActivity(),
                results);


        View view =  inflater.inflate(R.layout.fragment_search_result_list, container, false);

        ListView listView = (ListView) view.findViewById(R.id.fragment_search_list);
        listView.setAdapter(searchResultListAdapter);

        return view;

    }

}
