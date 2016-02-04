package com.lasalle.second.part.propertycross.services;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.lasalle.second.part.propertycross.PropertyCrossApplication;
import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.fragments.ProfileContentFragment;
import com.lasalle.second.part.propertycross.model.User;
import com.lasalle.second.part.propertycross.util.JSonUserBuilder;

import org.json.JSONException;
import org.json.JSONObject;

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

    public AccessToken getAccessToken() {
        return accessToken;
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

    public void manageSuccessfulLogin(AccessToken accessToken, final FragmentActivity activity) {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        // Application code
                        Log.v("LoginActivity", response.toString());

                        parseResponse(response.getJSONObject());

                        FragmentManager manager = activity.getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.profile_activity_content, new ProfileContentFragment());
                        transaction.commit();
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    protected void parseResponse(JSONObject response) {
        try {
            User user = JSonUserBuilder.createPropertyFromSearchResultJson(response);
            ApplicationServiceFactory.getInstance(PropertyCrossApplication.getContext()).getFacebookService().setUser(user);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
