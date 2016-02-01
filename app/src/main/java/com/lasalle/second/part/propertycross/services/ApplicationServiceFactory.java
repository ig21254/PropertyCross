package com.lasalle.second.part.propertycross.services;

import android.content.Context;

import com.lasalle.second.part.propertycross.repositories.impl.DbFavoritesRepo;
import com.lasalle.second.part.propertycross.repositories.impl.DbSearchHistoryRepo;
import com.lasalle.second.part.propertycross.repositories.impl.FavoritesRepoWebService;
import com.lasalle.second.part.propertycross.repositories.impl.PropertyRepoFile;
import com.lasalle.second.part.propertycross.repositories.impl.PropertyRepoWebService;

public class ApplicationServiceFactory {

    protected static ApplicationServiceFactory instance = null;

    private PropertyService propertyService;
    private FacebookService facebookService;
    private LocationService locationService;
    private Context context;

    protected ApplicationServiceFactory(Context context) {
        this.context = context;
        this.propertyService = new PropertyService(
                new PropertyRepoWebService(),
                new DbSearchHistoryRepo(context),
                new DbFavoritesRepo(context),
                new FavoritesRepoWebService());
        this.facebookService = new FacebookService();
        this.locationService = new LocationService(context);

    }

    public static ApplicationServiceFactory getInstance(Context context) {
        if(instance == null) {
            instance = new ApplicationServiceFactory(context);
        }

        return instance;
    }

    public PropertyService getPropertyService() {
        return propertyService;
    }

    public FacebookService getFacebookService() {
        return facebookService;
    }

    public LocationService getLocationService() {
        return locationService;
    }
}
