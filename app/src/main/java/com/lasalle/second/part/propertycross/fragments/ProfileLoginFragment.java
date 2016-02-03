package com.lasalle.second.part.propertycross.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.services.ApplicationServiceFactory;

/**
 * Created by Eduard on 26/01/2016.
 */
public class ProfileLoginFragment extends Fragment {

    private LoginButton facebookLoginButton;
    private TextView info;
    private CallbackManager callbackManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_login, container, false);

        facebookLoginButton = (LoginButton) view.findViewById(R.id.facebook_login_button);
        info = (TextView) view.findViewById(R.id.facebook_login_info);

        facebookLoginButton.setFragment(this);

        callbackManager = ApplicationServiceFactory.getInstance(getContext())
                .getFacebookService().getCallbackManager();
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ApplicationServiceFactory.getInstance(getContext())
                        .getFacebookService().setAccessToken(loginResult.getAccessToken());

                info.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );

                Toast toast = Toast.makeText(
                        getContext(),
                        getString(R.string.facebook_login_successful),
                        Toast.LENGTH_LONG);
                toast.show();

                Log.d("FacebookFragment", "Success!!");
            }

            @Override
            public void onCancel() {
                info.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException exception) {
                info.setText("Login attempt failed.");

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
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
