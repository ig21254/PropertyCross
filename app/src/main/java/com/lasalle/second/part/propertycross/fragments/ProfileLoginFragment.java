package com.lasalle.second.part.propertycross.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.lasalle.second.part.propertycross.services.FacebookService;

import java.util.Arrays;

/**
 * Created by Eduard on 26/01/2016.
 */
public class ProfileLoginFragment extends Fragment {

    private TextView info;
    private CallbackManager callbackManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_login, container, false);

        LoginButton facebookLoginButton = (LoginButton) view.findViewById(R.id.facebook_login_button);
        info = (TextView) view.findViewById(R.id.facebook_login_info);

        facebookLoginButton.setFragment(this);
        facebookLoginButton.setReadPermissions(Arrays.asList("public_profile, email"));

        callbackManager = ApplicationServiceFactory.getInstance(getContext())
                .getFacebookService().getCallbackManager();
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                FacebookService facebookService = ApplicationServiceFactory.getInstance(getContext())
                        .getFacebookService();

                facebookService.setAccessToken(loginResult.getAccessToken());

                facebookService.manageSuccessfulLogin(loginResult.getAccessToken(), getActivity());
                showToast(getString(R.string.facebook_login_successful));

                // Log.d("FacebookFragment", "Success!!");
            }

            @Override
            public void onCancel() {
                showToast("");
            }

            @Override
            public void onError(FacebookException exception) {
                showToast(getString(R.string.facebook_login_error));
                // Log.d("FacebookFragment", "Error :(");
            }
        });
        
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    protected void showToast(String message) {
        Toast toast = Toast.makeText(
                getContext(),
                message,
                Toast.LENGTH_LONG);
        toast.show();
    }


}
