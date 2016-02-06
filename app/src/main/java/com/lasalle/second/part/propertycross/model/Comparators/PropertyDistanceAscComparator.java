package com.lasalle.second.part.propertycross.model.Comparators;

import android.location.Location;

import com.lasalle.second.part.propertycross.PropertyCrossApplication;
import com.lasalle.second.part.propertycross.model.Property;
import com.lasalle.second.part.propertycross.services.ApplicationServiceFactory;

import java.util.Comparator;

/**
 * Created by Eduard on 06/02/2016.
 */
public class PropertyDistanceAscComparator implements Comparator<Property> {
    @Override
    public int compare(Property lhs, Property rhs) {
        Location location = ApplicationServiceFactory.getInstance(PropertyCrossApplication
                .getContext()).getLocationService().getLastSearchedLocation();

        Location lhLocation = new Location("");
        lhLocation.setLongitude(lhs.getLongitude());
        lhLocation.setLatitude(lhs.getLatitude());

        Location rhLocation = new Location("");
        rhLocation.setLongitude(rhs.getLongitude());
        rhLocation.setLatitude(rhs.getLatitude());


        return Float.compare(location.distanceTo(lhLocation), location.distanceTo(rhLocation));
    }

}
