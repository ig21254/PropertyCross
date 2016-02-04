package com.lasalle.second.part.propertycross.services;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.lasalle.second.part.propertycross.PropertyCrossApplication;
import com.lasalle.second.part.propertycross.model.User;

/**
 * Created by Eduard on 26/01/2016.
 */
public class FacebookService {
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;
    private User user;


    public FacebookService() {
        FacebookSdk.sdkInitialize(PropertyCrossApplication.getContext());

        callbackManager = CallbackManager.Factory.create();
        user = new User();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };
    }

    private void updateWithToken(AccessToken newAccessToken) {
        accessToken = newAccessToken;
    }

    public CallbackManager getCallbackManager() {return callbackManager;}

    public void setAccessToken (AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isLogged() {
        return accessToken != null;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
