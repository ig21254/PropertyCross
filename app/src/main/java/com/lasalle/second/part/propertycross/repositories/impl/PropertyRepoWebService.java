package com.lasalle.second.part.propertycross.repositories.impl;

import android.util.Log;

import com.lasalle.second.part.propertycross.model.PropertySearch;
import com.lasalle.second.part.propertycross.repositories.PropertyRepo;
import com.lasalle.second.part.propertycross.util.VolleyRequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PropertyRepoWebService implements PropertyRepo {

    protected static final String SEARCH_BASE_URL = "propiedad/buscar/";
    protected static final String DETAILS_BASE_URL = "propiedad/";
    private final String PROPERTIES_ARRAY_NODE_NAME = "datos";
    private final String PAGE_NUMBER_NODE_NAME = "page";
    private final String ELEMENTS_PER_PAGE_NODE_NAME = "pageSize";
    private final String TOTAL_ELEMENTS_NODE_NAME = "total";
    private final int DEFAULT_TIMEOUT = 500;

    @Override
    public JSONArray searchProperties(final PropertySearch search) {
        Log.d(getClass().getName(), "Search Properties");
        try {
            return doHttpRequest(SEARCH_BASE_URL, "POST").getJSONArray(PROPERTIES_ARRAY_NODE_NAME);
        } catch (JSONException e) {
            return new JSONArray();
        }
    }

    @Override
    public JSONObject searchPropertyDetails(int id) {
        return doHttpRequest(DETAILS_BASE_URL+id, "GET");
    }

    protected JSONObject doHttpRequest(String baseUrl, String method) {
        HttpURLConnection c = null;
        final String completeUrl = VolleyRequestHandler.API_BASE_URL + baseUrl;
        JSONObject jsonObject = new JSONObject();
        try {
            URL u = new URL(completeUrl);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod(method);
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

                    jsonObject = new JSONObject(sb.toString());
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

        return jsonObject;
    }

}
