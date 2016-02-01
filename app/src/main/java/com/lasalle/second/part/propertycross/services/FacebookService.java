package com.lasalle.second.part.propertycross.services;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.lasalle.second.part.propertycross.PropertyCrossApplication;

/**
 * Created by Eduard on 26/01/2016.
 */
public class FacebookService {
    private static CallbackManager callbackManager;

    public FacebookService() {
        FacebookSdk.sdkInitialize(PropertyCrossApplication.getContext());
        callbackManager = CallbackManager.Factory.create();
    }

    public static CallbackManager getCallbackManager() {return CallbackManager.Factory.create();}
}
