package com.lasalle.second.part.propertycross.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.adapters.MapViewAdapter;
import com.lasalle.second.part.propertycross.model.Property;
import com.lasalle.second.part.propertycross.model.PropertyClusterItem;
import com.lasalle.second.part.propertycross.model.PropertySearch;
import com.lasalle.second.part.propertycross.services.ApplicationServiceFactory;
import com.lasalle.second.part.propertycross.services.PropertyService;

import java.util.HashMap;
import java.util.List;

public class SearchResultMapFragment extends Fragment implements
        OnMapReadyCallback,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnCameraChangeListener {

    private ClusterManager<PropertyClusterItem> clusterManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mapView = inflater.inflate(R.layout.fragment_search_result_map, container, false);

        final FrameLayout frameLayout = (FrameLayout) mapView.findViewById(R.id.searchLayout);
        final SearchResultMapFragment currentFragment = this;
        frameLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(currentFragment);
                frameLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        return mapView;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLngBounds bounds = new LatLngBounds(
                new LatLng(41.338669, 2.086029),
                new LatLng(41.450961, 2.22801));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));

        clusterManager = new ClusterManager<>(getActivity(), googleMap);
        googleMap.setOnCameraChangeListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager);

        addItemsToClusterManager();

        googleMap.addMarker(
                new MarkerOptions()
                        .position(new LatLng(41.3833, 2.1833)));
        googleMap.getUiSettings().setZoomControlsEnabled(true);

    }

    private void addItemsToClusterManager() {
        List<Property> propertyList = ApplicationServiceFactory.getInstance(getContext())
                .getPropertyService().getLastSearch().getResults();

        for (Property p: propertyList) {
            PropertyClusterItem pci = new PropertyClusterItem(p);
            clusterManager.addItem(pci);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
    }



}
