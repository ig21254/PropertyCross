package com.lasalle.second.part.propertycross.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.fragments.FavoritesListFragment;
import com.lasalle.second.part.propertycross.listeners.DrawerItemClickListener;

public class FavoritesContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_container);

        addFavoritesListFragment();
        setupToolbar();
        createDrawerList();
    }

    protected void addFavoritesListFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        FavoritesListFragment favoritesListFragment = new FavoritesListFragment();
        fragmentTransaction.add(
                R.id.favorites_container_layout,
                favoritesListFragment);
        // El commit es asincron.
        fragmentTransaction.commit();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.favorites_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle(generateTitle());
        setSupportActionBar(toolbar);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.favorites_drawerLayout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
    }

    protected void createDrawerList() {
        ListView drawerListView = (ListView) findViewById(R.id.favorites_navigation_drawer_list);
        String[] drawerList = DrawerItemClickListener.getItemsList();

        ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, drawerList);
        drawerListView.setAdapter(listAdapter);
        DrawerItemClickListener drawerItemClickListener = new DrawerItemClickListener(listAdapter, this);
        drawerListView.setOnItemClickListener(drawerItemClickListener);
    }

    @NonNull
    private CharSequence generateTitle() {
        return getString(R.string.favorites_toolbar_title);
    }

}
