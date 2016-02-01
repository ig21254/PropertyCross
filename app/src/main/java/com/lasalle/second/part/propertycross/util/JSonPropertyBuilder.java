package com.lasalle.second.part.propertycross.util;

import com.lasalle.second.part.propertycross.model.Property;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSonPropertyBuilder {

    public static final String RENT_NODE_NAME = "alquiler";
    public static final String FAVOURITE_NODE_NAME = "favorito";
    public static final String PROPERTY_ID_NODE_NAME = "idPropiedad";
    public static final String LATITUDE_NODE_NAME = "latitud";
    public static final String LONGITUDE_NODE_NAME = "longitud";
    public static final String SQUARE_FOOTAGE_NODE_NAME = "metros";
    public static final String PRICE_NODE_NAME = "precio";
    public static final String NAME_NODE_NAME = "titulo";


    public static Property createPropertyFromSearchResultJson(JSONObject jsonObject) throws JSONException {
        Property property = new Property();

        property.setRent(jsonObject.getBoolean(RENT_NODE_NAME));
        //property.setFavourite(jsonObject.getBoolean(FAVOURITE_NODE_NAME));
        property.setId(jsonObject.getString(PROPERTY_ID_NODE_NAME));
        property.setLatitude(JSonUtils.getFloat(jsonObject, LATITUDE_NODE_NAME));
        property.setLongitude(JSonUtils.getFloat(jsonObject, LONGITUDE_NODE_NAME));
        property.setSquareFootage(JSonUtils.getFloat(jsonObject, SQUARE_FOOTAGE_NODE_NAME));
        property.setPrice(JSonUtils.getFloat(jsonObject, PRICE_NODE_NAME));
        property.setName(jsonObject.getString(NAME_NODE_NAME));

        return property;
    }

    public static List<Property> createPropertyListFromFavoritesResultJson(JSONArray jsonArray) throws JSONException {
        List<Property> propertyList = new ArrayList<>();

        for (int i=0; i< jsonArray.length(); ++i) {
            propertyList.add(createPropertyFromFavoritesResultJson((JSONObject) jsonArray.get(i)));
        }
        return propertyList;
    }

    private static Property createPropertyFromFavoritesResultJson(JSONObject jsonObject) throws JSONException {
        Property property = new Property();

        property.setRent(jsonObject.getBoolean(RENT_NODE_NAME));
        property.setId(jsonObject.getString(PROPERTY_ID_NODE_NAME));
        property.setLatitude(JSonUtils.getFloat(jsonObject, LATITUDE_NODE_NAME));
        property.setLongitude(JSonUtils.getFloat(jsonObject, LONGITUDE_NODE_NAME));
        property.setSquareFootage(JSonUtils.getFloat(jsonObject, SQUARE_FOOTAGE_NODE_NAME));
        property.setPrice(JSonUtils.getFloat(jsonObject, PRICE_NODE_NAME));
        property.setName(jsonObject.getString(NAME_NODE_NAME));

        return property;
    }

}
