package com.lasalle.second.part.propertycross.util;

import com.lasalle.second.part.propertycross.model.Comment;
import com.lasalle.second.part.propertycross.model.Property;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JSonPropertyBuilder {

    public static final String RENT_NODE_NAME = "alquiler";
    public static final String FAVOURITE_NODE_NAME = "favorito";
    public static final String PROPERTY_ID_NODE_NAME = "idPropiedad";
    public static final String DETAIL_ID_NODE_NAME = "id";
    public static final String LATITUDE_NODE_NAME = "latitud";
    public static final String LONGITUDE_NODE_NAME = "longitud";
    public static final String SQUARE_FOOTAGE_NODE_NAME = "metros";
    public static final String PRICE_NODE_NAME = "precio";
    public static final String NAME_NODE_NAME = "titulo";
    public static final String LAST_QUERY_NODE_NAME = "anteriorConsulta";
    public static final String CITY_NAME_NODE_NAME = "ciudad";
    public static final String POSTAL_CODE_NODE_NAME = "cp";
    public static final String DESCRIPTION_NODE_NAME = "descripcion";
    public static final String ADDRESS_NODE_NAME = "direccion";
    public static final String EMAIL_NODE_NAME = "emailPropietario";
    public static final String PHONE_NODE_NAME = "telefonoPropietario";
    public static final String COMMENTS_NODE_NAME = "comentarios";
    public static final String AUTHOR_COMMENT_NODE_NAME = "autor";
    public static final String DATE_COMMENT_NODE_NAME = "fecha";
    public static final String TEXT_COMMENT_NODE_NAME = "texto";


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

    public static Property createPropertyFromDetailsJson(JSONObject jsonObject) throws JSONException {
        Property property = new Property();

        property.setId(jsonObject.getString(DETAIL_ID_NODE_NAME));
        property.setRent(jsonObject.getBoolean(RENT_NODE_NAME));
        property.setLastQuery(new Date(jsonObject.getLong(LAST_QUERY_NODE_NAME)));
        property.setCityName(jsonObject.getString(CITY_NAME_NODE_NAME));
        property.setPostalCode(jsonObject.getString(POSTAL_CODE_NODE_NAME));
        property.setDescription(jsonObject.getString(DESCRIPTION_NODE_NAME));
        property.setAddress(jsonObject.getString(ADDRESS_NODE_NAME));
        property.setOwnerMail(jsonObject.getString(EMAIL_NODE_NAME));
        property.setLatitude(JSonUtils.getFloat(jsonObject, LATITUDE_NODE_NAME));
        property.setLongitude(JSonUtils.getFloat(jsonObject, LONGITUDE_NODE_NAME));
        property.setSquareFootage(JSonUtils.getFloat(jsonObject, SQUARE_FOOTAGE_NODE_NAME));
        property.setPrice(JSonUtils.getFloat(jsonObject, PRICE_NODE_NAME));
        property.setName(jsonObject.getString(NAME_NODE_NAME));
        property.setOwnerPhoneNumber(jsonObject.getString(PHONE_NODE_NAME));

        List<Comment> commentList= new ArrayList<>();
        JSONArray jsonArray = jsonObject.getJSONArray(COMMENTS_NODE_NAME);
        for (int i=0; i< jsonArray.length(); ++i) {
            commentList.add(createCommentFromJson(jsonArray.getJSONObject(i)));
        }

        return property;
    }

    private static Comment createCommentFromJson(JSONObject jsonObject) throws JSONException {
        Comment comment = new Comment();
        
        comment.setAuthor(jsonObject.getString(AUTHOR_COMMENT_NODE_NAME));
        comment.setDate(new Date(jsonObject.getLong(DATE_COMMENT_NODE_NAME)));
        comment.setText(jsonObject.getString(TEXT_COMMENT_NODE_NAME));

        return comment;
    }

}
