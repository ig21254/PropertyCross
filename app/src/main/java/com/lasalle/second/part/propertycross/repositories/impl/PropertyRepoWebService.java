package com.lasalle.second.part.propertycross.repositories.impl;

import android.util.Log;

import com.lasalle.second.part.propertycross.model.PropertySearch;
import com.lasalle.second.part.propertycross.repositories.PropertyRepo;
import com.lasalle.second.part.propertycross.util.VolleyRequestHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PropertyRepoWebService implements PropertyRepo {

    protected static final String SEARCH_BASE_URL = "propiedad/buscar/";
    private final String PROPERTIES_ARRAY_NODE_NAME = "datos";
    private final String PAGE_NUMBER_NODE_NAME = "page";
    private final String ELEMENTS_PER_PAGE_NODE_NAME = "pageSize";
    private final String TOTAL_ELEMENTS_NODE_NAME = "total";
    private final int DEFAULT_TIMEOUT = 500;

    @Override
    public JSONArray searchProperties(final PropertySearch search) {
        Log.d(getClass().getName(), "Search Properties");
        return doHttpRequest(search, 1);
    }

    protected JSONArray doHttpRequest(final PropertySearch search, final int currentPage) {
        HttpURLConnection c = null;
        final String completeUrl = VolleyRequestHandler.API_BASE_URL + SEARCH_BASE_URL + currentPage;
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

        Log.d(getClass().getName(), jsonArray.toString());
        return jsonArray;
    }

}
