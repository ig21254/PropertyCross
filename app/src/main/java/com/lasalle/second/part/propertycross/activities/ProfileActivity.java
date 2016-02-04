package com.lasalle.second.part.propertycross.activities;

import android.os.Bundle;
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
import com.lasalle.second.part.propertycross.fragments.ProfileContentFragment;
import com.lasalle.second.part.propertycross.fragments.ProfileLoginFragment;
import com.lasalle.second.part.propertycross.listeners.DrawerItemClickListener;
import com.lasalle.second.part.propertycross.services.ApplicationServiceFactory;

/**
 * Created by Eduard on 25/01/2016.
 */
public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_container);
        setupToolbar();
        createDrawerList();

        if (ApplicationServiceFactory.getInstance(getApplicationContext()).getFacebookService()
                .isLogged())
        {
            addProfileContentFragment();
        }
        else {
            addProfileLoginFragment();
        }
    }

    protected void addProfileLoginFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        ProfileLoginFragment loginFragment = new ProfileLoginFragment();
        fragmentTransaction.add(
                R.id.profile_activity_content,
                loginFragment);
        fragmentTransaction.commit();
    }

    protected void addProfileContentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        ProfileContentFragment contentFragment = new ProfileContentFragment();
        fragmentTransaction.add(
                R.id.profile_activity_content,
                contentFragment);
        fragmentTransaction.commit();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileActivityToolbar);
        toolbar.setTitle(getString(R.string.profile_title));
        setSupportActionBar(toolbar);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.profile_drawerLayout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();
    }

    protected void createDrawerList() {
        ListView drawerListView = (ListView) findViewById(R.id.profile_navigation_drawer_list);
        String[] drawerList = DrawerItemClickListener.getItemsList();

        ListAdapter listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, drawerList);
        drawerListView.setAdapter(listAdapter);
        DrawerItemClickListener drawerItemClickListener = new DrawerItemClickListener(listAdapter, this);
        drawerListView.setOnItemClickListener(drawerItemClickListener);
    }
}
