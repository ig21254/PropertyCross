package com.lasalle.second.part.propertycross.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toolbar;

import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.adapters.SearchResultListAdapter;
import com.lasalle.second.part.propertycross.model.Property;
import com.lasalle.second.part.propertycross.services.ApplicationServiceFactory;
import com.lasalle.second.part.propertycross.services.PropertyService;

import java.util.List;

/**
 * Created by Eduard on 17/01/2016.
 */
public class FavoritesListFragment extends Fragment {
    protected int numFavorites;
    protected List<Property> favorites;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        PropertyService propertyService = ApplicationServiceFactory.getInstance(getActivity())
                .getPropertyService();

        favorites = propertyService.getFavorites();

        numFavorites = favorites.size();

        SearchResultListAdapter propertiesListAdapter = new SearchResultListAdapter(
                getActivity(),
                favorites);

        View view =  inflater.inflate(R.layout.fragment_search_result_list, container, false);

        ListView listView = (ListView) view.findViewById(R.id.fragment_search_list);
        listView.setAdapter(propertiesListAdapter);

        return view;
    }

    public int getNumFavorites() {
        return numFavorites;
    }

    @NonNull
    private CharSequence generateTitle() {
        return new StringBuilder()
                .append(getString(R.string.favorites_toolbar_title))
                .append(" (")
                .append(numFavorites)
                .append(")").toString();
    }

}
