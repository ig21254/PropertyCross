package com.lasalle.second.part.propertycross.activities;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lasalle.second.part.propertycross.PropertyCrossApplication;
import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.listeners.DrawerItemClickListener;
import com.lasalle.second.part.propertycross.model.PropertySearch;
import com.lasalle.second.part.propertycross.model.SearchHistory;
import com.lasalle.second.part.propertycross.services.ApplicationServiceFactory;
import com.lasalle.second.part.propertycross.services.LocationService;
import com.lasalle.second.part.propertycross.services.PropertyService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    private static final String TEXT1 = "text1";
    private static final String TEXT2 = "text2";
    private boolean hasRecentSearches;
    private List<Map<String, String>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApplicationServiceFactory.getInstance(this).getLocationService().startTrackingLocation();

        final ListAdapter listAdapter = updateRecentSearches();
        ListView listView = (ListView) findViewById(R.id.main_previous_results_list_view);
        listView.setAdapter(listAdapter);

        EditText editText = (EditText) findViewById(R.id.main_search_edit_text);
        editText.setOnEditorActionListener(this);

        setupToolbar();
        setupLocationButton();
        createDrawerList();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;

        final boolean isKeyboardEnter = (event != null) &&
                (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) &&
                (event.getAction() == KeyEvent.ACTION_DOWN);


        if ((actionId == EditorInfo.IME_ACTION_SEARCH) || isKeyboardEnter) {
            Log.d(this.getLocalClassName(), "Search Action!");
            handled = true;

            hideKeyboard(v);

            CheckBox rentBox = (CheckBox) findViewById(R.id.main_search_option_rent);
            CheckBox sellBox = (CheckBox) findViewById(R.id.main_search_option_buy);

            String query = v.getText().toString();
            v.setText("");
            boolean rent = rentBox.isChecked();
            boolean sell = sellBox.isChecked();

            PropertySearch propertySearch = new PropertySearch(query, rent, sell);
            AsyncSearch asyncSearch = new AsyncSearch();
            asyncSearch.execute(propertySearch);

            displayWaitingButton(asyncSearch);

        }
        return handled;
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.mainActivityToolbar);
        toolbar.setTitle(getString(R.string.main_activity_title));
        setSupportActionBar(toolbar);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
    }

    private void setupLocationButton() {
        final ImageView locationImage = (ImageView) findViewById(R.id.locationIcon);
        locationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApplicationServiceFactory serviceFactory = ApplicationServiceFactory.getInstance(getApplicationContext());
                LocationService locationService = serviceFactory.getLocationService();
                Location location = locationService.getLastKnownLocation();
                if(location == null) {
                    Toast.makeText(MainActivity.this, "Null location", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, locationService.getLastKnownLocationAsAddress(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private ListAdapter updateRecentSearches() {
        final String[] fromMapKey = new String[] {TEXT1, TEXT2};
        final int[] toLayoutId = new int[] {android.R.id.text1, android.R.id.text2};

        list = new ArrayList<>();

        populateRecentSearches();

        return new SimpleAdapter(this, list, android.R.layout.simple_list_item_2, fromMapKey, toLayoutId);
    }

    private void populateRecentSearches() {
        PropertyService propertyService = ApplicationServiceFactory.
                getInstance(PropertyCrossApplication.getContext()).getPropertyService();

        List<SearchHistory> recentSearches = propertyService.getRecentSearches();

        if (recentSearches.size() == 0) {
            addListItem(list, getString(R.string.recent_searches_no_results), "");
            // S'ha de posar hasRecentSearches a fals just després d'afegir-lo el text dummy per
            // sobreescriure el resultat i marcar que no s'han fet cerques encara.
            hasRecentSearches = false;
        } else {
            for (SearchHistory sh : recentSearches) {
                addListItem(list, sh.getQuery(), createSubtitle(sh.getRentResults()+sh.getSellResults()));
            }
        }
    }

    private String createSubtitle(int numResults) {
        return numResults + " " + getString(numResults==1?
                R.string.recent_searches_single_result_subtitle:
                R.string.recent_searches_multiple_result_subtitle);
    }

    private void addListItem(List<Map<String, String>> list, String itemTitle, String itemSubtitle) {
        final Map<String, String> itemMap = new HashMap<>();
        itemMap.put(TEXT1, itemTitle);
        itemMap.put(TEXT2, itemSubtitle);

        if (!hasRecentSearches) {
            list.clear();
        }
        list.add(Collections.unmodifiableMap(itemMap));
        // Si hi ha més de 5 elements els eliminem.
        if (list.size() > 5) list.subList(5, list.size()).clear();
        hasRecentSearches = !list.isEmpty();
    }

    private void hideKeyboard(View view) {
        view.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected void displayWaitingButton(final AsyncSearch asyncSearch) {
        final RelativeLayout waitingLayout = (RelativeLayout) findViewById(R.id.floatingCancelLayout);
        waitingLayout.setVisibility(View.VISIBLE);

        FloatingActionButton cancelButton = (FloatingActionButton) findViewById(R.id.cancelSearchActionButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asyncSearch.cancel(true);
                waitingLayout.setVisibility(View.GONE);
            }
        });
    }

    private class AsyncSearch extends AsyncTask<PropertySearch, Void, Boolean> {

        private Context context;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.context = getApplicationContext();
        }

        @Override
        protected Boolean doInBackground(PropertySearch... params) {
            PropertyService propertyService = ApplicationServiceFactory.
                    getInstance(context).getPropertyService();
            return !propertyService.searchPropertiesCachingResult(params[0]).isEmpty();
        }

        @Override
        protected void onPostExecute(Boolean hasResults) {
            super.onPostExecute(hasResults);
            if(!isCancelled()) {
                RelativeLayout waitingLayout = (RelativeLayout) findViewById(R.id.floatingCancelLayout);
                waitingLayout.setVisibility(View.GONE);

                if(!hasResults) {
                    Toast toast = Toast.makeText(
                            context,
                            getString(R.string.no_results_found),
                            Toast.LENGTH_LONG);
                    toast.show();
                }
                else {
                    updateRecentSearches();
                    ApplicationServiceFactory.getInstance(getApplicationContext())
                            .getLocationService().stopTrackingLocation();
                    ApplicationServiceFactory.getInstance(getApplicationContext())
                            .getLocationService().storeGeoAddress();
                    Intent intent = new Intent(context, ResultsContainerActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        new AsyncFavoritesSearch().execute();
        return super.onOptionsItemSelected(item);
    }

    public class AsyncFavoritesSearch extends AsyncTask<Void, Void, Boolean> {

        private Context context;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.context = getApplicationContext();
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
            startActivity(intent);
        }
    }

    protected void createDrawerList() {
        ListView drawerListView = (ListView) findViewById(R.id.main_navigation_drawer_list);
        String[] drawerList = DrawerItemClickListener.getItemsList();

        ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, drawerList);
        drawerListView.setAdapter(listAdapter);
        DrawerItemClickListener drawerItemClickListener = new DrawerItemClickListener(listAdapter, this);
        drawerListView.setOnItemClickListener(drawerItemClickListener);
    }

}
