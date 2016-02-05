package com.lasalle.second.part.propertycross.services;

import android.util.Log;

import com.lasalle.second.part.propertycross.listeners.PropertyServiceListener;
import com.lasalle.second.part.propertycross.model.Property;
import com.lasalle.second.part.propertycross.model.PropertySearch;
import com.lasalle.second.part.propertycross.model.SearchHistory;
import com.lasalle.second.part.propertycross.repositories.FavoritesRepo;
import com.lasalle.second.part.propertycross.repositories.PropertyRepo;
import com.lasalle.second.part.propertycross.repositories.SearchHistoryRepo;
import com.lasalle.second.part.propertycross.repositories.impl.FavoritesRepoWebService;
import com.lasalle.second.part.propertycross.util.JSonPropertyBuilder;
import com.lasalle.second.part.propertycross.util.JSonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PropertyService {

    private PropertyRepo propertyRepo;
    private SearchHistoryRepo searchHistoryRepo;
    private FavoritesRepo favoritesRepo;
    private FavoritesRepoWebService favoritesRepoWebService;
    private PropertySearch lastSearch;
    private List<Property> favorites;
    private Property lastDetail;

    private List<PropertyServiceListener> listeners;


    public PropertyService(PropertyRepo propertyRepo,
                           SearchHistoryRepo searchHistoryRepo,
                           FavoritesRepo favoritesRepo,
                           FavoritesRepoWebService favoritesRepoWebService) {
        this.propertyRepo = propertyRepo;
        this.searchHistoryRepo = searchHistoryRepo;
        this.favoritesRepo = favoritesRepo;
        this.favoritesRepoWebService = favoritesRepoWebService;
        this.lastSearch = new PropertySearch();
        listeners = new ArrayList<>();
        this.lastDetail = new Property();
    }


    /*
     * Properties Search
     */
    public List<Property> searchPropertiesCachingResult(PropertySearch currentSearch) {
        Log.d("Prperty Service", "searchPropertiesCachingResult");
        return searchProperties(currentSearch, true);
    }

    public List<Property> searchPropertiesWithoutCaching(PropertySearch currentSearch) {
        return searchProperties(currentSearch, false);
    }

    public PropertySearch getLastSearch() {
        return lastSearch;
    }

    protected List<Property> searchProperties(final PropertySearch currentSearch, final boolean cacheResults) {
        Log.d("Prperty Service", "searchProperties");
        JSONArray propertiesJsonArray = getCachedSearch(currentSearch);
        Log.d("Prperty Service", "getCachedSearch");
        if(propertiesJsonArray.length() == 0) {
            Log.d("Prperty Service", "No cached properties");
            propertiesJsonArray = propertyRepo.searchProperties(currentSearch);
        }

        List<Property> propertyList = createPropertyListFromSearchResult(
                currentSearch,
                propertiesJsonArray,
                cacheResults);
        return propertyList;
    }

    protected List<Property> createPropertyListFromSearchResult(PropertySearch currentSearch,
                                                      JSONArray propertiesJsonArray,
                                                      boolean cacheResults) {

        final boolean includeRent = currentSearch.isRent();
        final boolean includeSell = currentSearch.isSell();
        JsonParseResults parseResults = parseFromJson(propertiesJsonArray, includeRent, includeSell);

        if(cacheResults) {
            final int totalRent = parseResults.getTotalRent();
            final int totalSell = parseResults.getTotalSell();
            if (totalRent + totalSell > 0) {
                cacheResults(currentSearch, propertiesJsonArray, totalRent, totalSell);
            }
        }

        List<Property> resultList = parseResults.getPropertyList();
        currentSearch.setResults(resultList);
        lastSearch = currentSearch;
        notifyListeners(lastSearch);

        return resultList;
    }

    protected JsonParseResults parseFromJson(JSONArray jsonArray, boolean includeRent, boolean includeSell) {
        List<Property> propertyList = new ArrayList<>();
        Integer totalRent = 0;
        Integer totalSell = 0;

        try {
            int jsonArraySize = jsonArray.length();

            for(int index = 0; index < jsonArraySize; ++index) {
                JSONObject childObject = jsonArray.getJSONObject(index);
                Property property = JSonPropertyBuilder.createPropertyFromSearchResultJson(childObject);

                if(property.isRent()) {
                    ++totalRent;
                    if(includeRent) {
                        propertyList.add(property);
                    }
                }
                else {
                    ++totalSell;
                    if(includeSell) {
                        propertyList.add(property);
                    }
                }
            }

        } catch (JSONException exc) {
            Log.e(this.getClass().getName(), "Exception", exc);
        }

        return new JsonParseResults(propertyList, totalRent, totalSell);
    }

    protected void cacheResults(PropertySearch search, JSONArray resultsArray, int totalRent, int totalSell) {
        boolean cached = searchHistoryRepo.addSearchResult(search, totalRent, totalSell, resultsArray.toString());

        if(!cached) {
            Log.e(getClass().getName(), "Result was not cached properly");
        }
    }

    protected JSONArray getCachedSearch(PropertySearch search) {
        JSONArray cachedSearchResult = new JSONArray();

        SearchHistory searchHistory = searchHistoryRepo.getSearchHistoryFromSearch(search);

        if(searchHistory != null) {
            cachedSearchResult = JSonUtils.createArrayFromString(searchHistory.getRawResults());
        }

        return cachedSearchResult;
    }

    protected class JsonParseResults {
        private List<Property> propertyList;
        private int totalRent;
        private int totalSell;

        public JsonParseResults(List<Property> propertyList, int totalRent, int totalSell) {
            this.propertyList = propertyList;
            this.totalRent = totalRent;
            this.totalSell = totalSell;
        }

        public List<Property> getPropertyList() {
            return propertyList;
        }

        public int getTotalRent() {
            return totalRent;
        }

        public int getTotalSell() {
            return totalSell;
        }
    }


    /*
     * Favorites Search
     */
    public List<Property> searchFavoritesCachingResults() {
        return searchFavorites(true);
    }

    public List<Property> searchFavoritesWithoutCachingResults() {
        return searchFavorites(false);
    }

    protected List<Property> searchFavorites(final boolean cached) {
        if (cached) {
            favorites = favoritesRepo.getFavoriteProperties();
        } else {
            favorites = favoritesRepoWebService.getFavoriteProperties();
        }
        return favorites;
    }

    public List<Property> getFavorites() {
        return favorites;
    }


    public List<SearchHistory> getRecentSearches() {
        return searchHistoryRepo.getRecentSearches();
    }

    public void addListener(PropertyServiceListener propertyServiceListener) {
        listeners.add(propertyServiceListener);
    }

    protected void notifyListeners(Object data) {
        for (PropertyServiceListener l: listeners) {
            l.onDataLoaded(data);
            Log.d("PropertyService", "NOTIFY");
        }
    }



    /*
     * Property Details Search
     */
    public Property searchPropertyDetailsCachingResult(int id) {
        Log.d("Property Service", "searchPropertiesCachingResult");
        return searchPropertyDetails(id, true);
    }

    public Property searchPropertyDetailsWithoutCaching(int id) {
        return searchPropertyDetails(id, false);
    }

    private Property searchPropertyDetails(int id, boolean cache) {
        JSONObject propertyDetailsJsonObject = null;
        if(propertyDetailsJsonObject == null) {
            propertyDetailsJsonObject = propertyRepo.searchPropertyDetails(id);
        }

        try {
            lastDetail = JSonPropertyBuilder.createPropertyFromDetailsJson(propertyDetailsJsonObject);
            return lastDetail;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Property getLastDetail() {
        return lastDetail;
    }
}
