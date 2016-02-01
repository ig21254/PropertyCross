package com.lasalle.second.part.propertycross.repositories.impl;

import android.util.Log;

import com.lasalle.second.part.propertycross.model.Property;
import com.lasalle.second.part.propertycross.repositories.FavoritesRepo;
import com.lasalle.second.part.propertycross.util.JSonPropertyBuilder;
import com.lasalle.second.part.propertycross.util.VolleyRequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduard on 24/01/2016.
 */
public class FavoritesRepoWebService implements FavoritesRepo {

    protected static final String FAVORITES_BASE_URL = "favoritos";
    private final String PROPERTIES_ARRAY_NODE_NAME = "datos";
    private final int DEFAULT_TIMEOUT = 300;


    @Override
    public List<Property> getFavoriteProperties() {
        JSONArray favoritesArray = doHttpRequest();
        try {
            return JSonPropertyBuilder.createPropertyListFromFavoritesResultJson(favoritesArray);
        } catch (JSONException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean addFavorite(Property property) {
        return false;
    }

    protected JSONArray doHttpRequest() {
        HttpURLConnection c = null;
        final String completeUrl = VolleyRequestHandler.API_BASE_URL + FAVORITES_BASE_URL;
        JSONArray jsonArray = new JSONArray();
        try {
            URL u = new URL(completeUrl);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("POST");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(DEFAULT_TIMEOUT);
            c.setReadTimeout(DEFAULT_TIMEOUT);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();

                    JSONObject jsonObject = new JSONObject(sb.toString());
                    jsonArray = jsonObject.getJSONArray(PROPERTIES_ARRAY_NODE_NAME);
            }

        } catch (Exception ex) {
            Log.e(getClass().getName(), "Exception", ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Log.e(getClass().getName(), "Exception", ex);
                }
            }
        }

        return jsonArray;
    }
}
