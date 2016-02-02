package com.lasalle.second.part.propertycross.services;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.lasalle.second.part.propertycross.PropertyCrossApplication;

/**
 * Created by Eduard on 26/01/2016.
 */
public class FacebookService {
    private CallbackManager callbackManager;
    private AccessToken accessToken;

    public FacebookService() {
        FacebookSdk.sdkInitialize(PropertyCrossApplication.getContext());
        callbackManager = CallbackManager.Factory.create();
        accessToken = null;
    }

    public CallbackManager getCallbackManager() {return CallbackManager.Factory.create();}

    public void setAccessToken (AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isLogged() {
        return accessToken != null;
    }
}
