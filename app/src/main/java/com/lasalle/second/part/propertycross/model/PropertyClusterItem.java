package com.lasalle.second.part.propertycross.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Eduard on 06/02/2016.
 */
public class PropertyClusterItem implements ClusterItem {

    private Property property;
    private LatLng position;

    public PropertyClusterItem(Property property) {
        this.property = property;
        position = new LatLng(property.getLatitude(), property.getLongitude());
    }

    @Override
    public LatLng getPosition() {
        return position;
    }
}
