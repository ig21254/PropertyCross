package com.lasalle.second.part.propertycross.util;

import android.util.Log;

import com.lasalle.second.part.propertycross.model.Property;
import com.lasalle.second.part.propertycross.model.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eduard on 03/02/2016.
 */
public class JSonUserBuilder {
    public static final String FACEBOOK_ID = "id";
    public static final String FACEBOOK_NAME = "first_name";
    public static final String FACEBOOK_SURNAME = "last_name";
    public static final String FACEBOOK_PICTURE = "picture";
    public static final String FACEBOOK_EMAIL = "email";

    public static User createPropertyFromSearchResultJson(JSONObject jsonObject) throws JSONException {
        User user = new User();

        user.setName(jsonObject.getString(FACEBOOK_NAME));
        user.setSurname(jsonObject.getString(FACEBOOK_SURNAME));
        user.setMail(jsonObject.getString(FACEBOOK_EMAIL));
        user.setPicture(jsonObject.getJSONObject(FACEBOOK_PICTURE).getJSONObject("data").getString("url"));

        Log.d("JSonUserBuilder", user.getPicture());

        return user;
    }

}
