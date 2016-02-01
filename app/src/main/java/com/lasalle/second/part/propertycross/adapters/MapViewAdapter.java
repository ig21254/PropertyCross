package com.lasalle.second.part.propertycross.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.model.Property;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapViewAdapter implements GoogleMap.InfoWindowAdapter {

    private HashMap<String, Property> propertyMap;
    private LayoutInflater layoutInflater;


    public MapViewAdapter(HashMap<String, Property> propertyMap, LayoutInflater layoutInflater) {
        this.propertyMap = propertyMap;
        this.layoutInflater = layoutInflater;


    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }
}
