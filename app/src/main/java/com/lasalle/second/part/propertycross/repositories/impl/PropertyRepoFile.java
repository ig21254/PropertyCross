package com.lasalle.second.part.propertycross.repositories.impl;

import android.content.Context;
import android.util.Log;

import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.listeners.PropertyRepoListener;
import com.lasalle.second.part.propertycross.model.Property;
import com.lasalle.second.part.propertycross.model.PropertySearch;
import com.lasalle.second.part.propertycross.repositories.PropertyRepo;
import com.lasalle.second.part.propertycross.util.JSonPropertyBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class PropertyRepoFile implements PropertyRepo {

    private Context context;

    public PropertyRepoFile(Context context) {
        this.context = context;
    }

    @Override
    public JSONArray searchProperties(PropertySearch search) {
        String propertiesString = readStringFromFile(R.raw.rent_property_list_all);
        JSONArray jsonArray = getJsonArrayFromPropertyString(propertiesString);
        return jsonArray;
    }

    @Override
    public JSONObject searchPropertyDetails(int id) {
        return null;
    }

    protected String readStringFromFile(int file) {
        String fileContent = new String("");

        InputStream inputStream = context.getResources().openRawResource(file);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line);
            }
            fileContent = out.toString();
            reader.close();
        } catch (IOException exc) {
            Log.e(this.getClass().getName(), "Exception", exc);
        } finally {
            try {
                if(inputStream != null) inputStream.close();
            } catch(Exception exc2) {
                Log.e(this.getClass().getName(), "Exception", exc2);
            }
        }

        return fileContent;

    }

    protected JSONArray getJsonArrayFromPropertyString(String propertiesString) {
        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject jsonObject = new JSONObject(propertiesString);
            jsonArray = jsonObject.getJSONArray("datos");
        } catch (JSONException exc) {
            Log.e(this.getClass().getName(), "Exception", exc);
        }

        return jsonArray;
    }

}
