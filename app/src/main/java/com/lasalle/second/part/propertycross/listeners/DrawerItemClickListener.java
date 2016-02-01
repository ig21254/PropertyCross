package com.lasalle.second.part.propertycross.listeners;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.lasalle.second.part.propertycross.PropertyCrossApplication;
import com.lasalle.second.part.propertycross.activities.FavoritesContainerActivity;
import com.lasalle.second.part.propertycross.activities.ProfileActivity;
import com.lasalle.second.part.propertycross.services.ApplicationServiceFactory;
import com.lasalle.second.part.propertycross.services.PropertyService;

/**
 * Created by Eduard on 25/01/2016.
 */
public class DrawerItemClickListener implements ListView.OnItemClickListener {

    public static final String PROFILE = "Profile";
    public static final String FAVORITES = "Favorites";
    public static final int PROFILE_CODE = 12345;

    private ListAdapter listAdapter;
    private AppCompatActivity appCompatActivity;

    public DrawerItemClickListener(ListAdapter listAdapter, AppCompatActivity appCompatActivity) {
        this.listAdapter = listAdapter;
        this.appCompatActivity = appCompatActivity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = (String) listAdapter.getItem(position);

        switch (item) {
            case PROFILE:
                Intent intent = new Intent(PropertyCrossApplication.getContext(), ProfileActivity.class);
                appCompatActivity.startActivityForResult(intent, PROFILE_CODE);
                break;

            case FAVORITES:
                new AsyncFavoritesSearch().execute();
                break;
        }
    }

    public static String[] getItemsList() {
        return new String[]{PROFILE, FAVORITES};
    }

    private class AsyncFavoritesSearch extends AsyncTask<Void, Void, Boolean> {

        private Context context;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.context = PropertyCrossApplication.getContext();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            PropertyService propertyService = ApplicationServiceFactory.
                    getInstance(context).getPropertyService();
            return !propertyService.searchFavoritesWithoutCachingResults().isEmpty();
        }

        @Override
        protected void onPostExecute(Boolean hasResults) {
            super.onPostExecute(hasResults);

            Intent intent = new Intent(context, FavoritesContainerActivity.class);
            appCompatActivity.startActivity(intent);
        }
    }

}
