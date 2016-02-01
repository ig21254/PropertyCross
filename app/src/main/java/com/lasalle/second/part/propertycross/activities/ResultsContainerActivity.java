package com.lasalle.second.part.propertycross.activities;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.fragments.SearchResultContainerFragment;
import com.lasalle.second.part.propertycross.fragments.SearchResultListFragment;
import com.lasalle.second.part.propertycross.fragments.SearchResultMapFragment;

public class ResultsContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_container);

        addResultListFragment();
    }

    protected void addResultListFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        //SearchResultContainerFragment containerFragment = new SearchResultContainerFragment();
        SearchResultMapFragment containerFragment = new SearchResultMapFragment();
        fragmentTransaction.add(
                R.id.result_container_layout,
                containerFragment);
        fragmentTransaction.commit();
    }


}
