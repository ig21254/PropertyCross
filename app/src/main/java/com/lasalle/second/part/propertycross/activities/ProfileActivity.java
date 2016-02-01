package com.lasalle.second.part.propertycross.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.fragments.ProfileLoginFragment;

/**
 * Created by Eduard on 25/01/2016.
 */
public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile_container);

        Toolbar toolbar = (Toolbar) findViewById(R.id.profileActivityToolbar);
        toolbar.setTitle(getString(R.string.profile_title));
        setSupportActionBar(toolbar);

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,  mDrawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        addProfileLoginFragment();
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
}
