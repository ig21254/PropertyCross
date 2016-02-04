package com.lasalle.second.part.propertycross.activities;


import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.fragments.ProfileContentFragment;
import com.lasalle.second.part.propertycross.fragments.SearchResultContainerFragment;
import com.lasalle.second.part.propertycross.fragments.SearchResultListFragment;
import com.lasalle.second.part.propertycross.fragments.SearchResultMapFragment;

public class ResultsContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_container);

        addResultListFragment(getResources().getConfiguration().orientation);
    }

    protected void addResultListFragment(int orientation) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        Fragment containerFragment;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            containerFragment = new SearchResultMapFragment();
        }
        else {
            containerFragment = new SearchResultContainerFragment();
        }

        fragmentTransaction.add(
                R.id.result_container_layout,
                containerFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        updateResultListFragment(newConfig.orientation);
    }

    protected void updateResultListFragment(int orientation) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        Fragment containerFragment;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            containerFragment = new SearchResultMapFragment();
        }
        else {
            containerFragment = new SearchResultContainerFragment();
        }

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.result_container_layout, containerFragment);
        transaction.commit();
    }
}
