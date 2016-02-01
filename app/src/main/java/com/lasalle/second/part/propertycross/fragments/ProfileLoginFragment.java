package com.lasalle.second.part.propertycross.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.services.FacebookService;

/**
 * Created by Eduard on 26/01/2016.
 */
public class ProfileLoginFragment extends Fragment {

    private LoginButton facebookLoginButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_login, container, false);

        facebookLoginButton = (LoginButton) view.findViewById(R.id.facebook_login_button);
        facebookLoginButton.setReadPermissions("users_friends");
        facebookLoginButton.setFragment(this);
        facebookLoginButton.registerCallback(FacebookService.getCallbackManager(), new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast toast = Toast.makeText(
                        getContext(),
                        getString(R.string.facebook_login_successful),
                        Toast.LENGTH_LONG);
                toast.show();
                Log.d("FacebookFragment", "Success!!");
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Toast toast = Toast.makeText(
                        getContext(),
                        getString(R.string.facebook_login_error),
                        Toast.LENGTH_LONG);
                toast.show();
                Log.d("FacebookFragment", "Error :(");
            }
        });
        
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FacebookService.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }
}
