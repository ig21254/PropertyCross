package com.lasalle.second.part.propertycross.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lasalle.second.part.propertycross.R;

/**
 * Created by eduard on 02/02/2016.
 */
public class ProfileContentFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_content, container, false);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
