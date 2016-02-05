package com.lasalle.second.part.propertycross.repositories;

import com.lasalle.second.part.propertycross.model.PropertySearch;

import org.json.JSONArray;
import org.json.JSONObject;

public interface PropertyRepo {

    public JSONArray searchProperties(PropertySearch search);

    public JSONObject searchPropertyDetails(int id);

}
