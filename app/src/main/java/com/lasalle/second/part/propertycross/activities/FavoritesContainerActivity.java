package com.lasalle.second.part.propertycross.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.fragments.FavoritesListFragment;

public class FavoritesContainerActivity extends AppCompatActivity {

    protected int numFavorites;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_container);

        addFavoritesListFragment();
        createToolbar();
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

    protected void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.favorites_toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        CharSequence title = generateTitle();
        getSupportActionBar().setTitle(title);
    }

    @NonNull
    private CharSequence generateTitle() {
        return getString(R.string.favorites_toolbar_title);
    }

}
