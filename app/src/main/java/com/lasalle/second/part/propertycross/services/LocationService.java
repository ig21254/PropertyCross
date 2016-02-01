package com.lasalle.second.part.propertycross.services;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;
import java.util.Locale;

public class LocationService implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    private static final int REQUEST_CODE = 1;

    private GoogleApiClient googleApiClient;
    private Context context;
    private Location lastKnownLocation;
    private boolean gpsEnabled;
    private boolean networkEnabled;
    private LocationManager locationManager;
    private boolean updatesEnabled;

    public LocationService(Context context) {
        this.context = context;
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();

        if(!hasLocationPermission()) {
            requestPermissions();
        }

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        checkLocationServices();
    }

    public void startTrackingLocation() {
        if(canRequestLocation()) {
            updatesEnabled = true;

            String trackerProvider = LocationManager.GPS_PROVIDER;

            if(!gpsEnabled) {
                trackerProvider = LocationManager.NETWORK_PROVIDER;
            }


            locationManager.requestLocationUpdates(trackerProvider, 0, 0, this);
        }

    }

    public void stopTrackingLocation() {
        if(canRequestLocation() && updatesEnabled) {
            locationManager.removeUpdates(this);
        }
    }

    public Location getLastKnownLocation() {
        return lastKnownLocation;
    }

    public String getLastKnownLocationAsAddress() {
        String address = new String();
        try {
            Geocoder geocoder = new Geocoder(context, Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1);
            address = addressList.get(0).getAddressLine(0) + ", " +
                    addressList.get(0).getLocality();
        } catch (Exception e) {
            Log.e(getClass().getName(), "Exception", e);
        }

        return address;
    }

    public boolean canRequestLocation() {
        return gpsEnabled || networkEnabled;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onConnected(Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(getClass().getName(), "Location updated");
        lastKnownLocation = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    private void checkLocationServices() {
        gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions((Activity) context,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    private boolean hasLocationPermission() {
        return (PackageManager.PERMISSION_GRANTED ==
                context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }
}
