package com.lasalle.second.part.propertycross.repositories;

import com.lasalle.second.part.propertycross.model.PropertySearch;

import org.json.JSONArray;

public interface PropertyRepo {

    public JSONArray searchProperties(PropertySearch search);

}
