package com.lasalle.second.part.propertycross.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.model.User;
import com.lasalle.second.part.propertycross.services.ApplicationServiceFactory;

import java.io.InputStream;
import java.util.Arrays;

/**
 * Created by Eduard de Torres on 02/02/2016.
 */
public class ProfileContentFragment extends Fragment {

    private EditText username;
    private EditText password;
    private EditText name;
    private EditText surname;
    private EditText email;
    private Switch notifications;
    private EditText address;
    private SeekBar radius;
    private TextView radiusDistance;
    private ImageView profileImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_content, container, false);

        prepareView(view);
        manageFacebookButton(view);

        return view;
    }

    private void manageFacebookButton(View view) {
        LoginButton facebookLoginButton = (LoginButton) view.findViewById(R.id.profile_facebook_login_button);

        facebookLoginButton.setFragment(this);
        facebookLoginButton.setReadPermissions(Arrays.asList("public_profile, email"));

        CallbackManager callbackManager = ApplicationServiceFactory.getInstance(getContext())
                .getFacebookService().getCallbackManager();
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                ApplicationServiceFactory.getInstance(getContext())
                        .getFacebookService().setAccessToken(loginResult.getAccessToken());

                manageSuccessfulLogin(loginResult);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
            }
        });
    }

    protected void manageSuccessfulLogin(LoginResult loginResult) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.profile_activity_content, new ProfileLoginFragment());
        transaction.commit();
    }

    protected void prepareView (View view) {
        User user = ApplicationServiceFactory.getInstance(getContext()).getFacebookService().getUser();

        username = (EditText) view.findViewById(R.id.profile_username_input);
        username.setText(user.getUsername());

        password = (EditText) view.findViewById(R.id.profile_password_input);

        name = (EditText) view.findViewById(R.id.profile_name_input);
        name.setText(user.getName());

        surname = (EditText) view.findViewById(R.id.profile_surname_input);
        surname.setText(user.getSurname());

        profileImage = (ImageView) view.findViewById(R.id.profile_picture_image);
        new DownloadImageWithURLTask().execute(user.getPicture());

        email = (EditText) view.findViewById(R.id.profile_mail_input);
        email.setText(user.getMail());

        notifications = (Switch) view.findViewById(R.id.profile_notification_switch);

        address = (EditText) view.findViewById(R.id.profile_location_input);

        radius = (SeekBar) view.findViewById(R.id.profile_radius_seekbar);
        radius.setMax(10);
        radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                CharSequence text = String.valueOf(progress).concat(" km");
                radiusDistance.setText(text);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        radiusDistance = (TextView) view.findViewById(R.id.profile_radius_distance);
        CharSequence text = String.valueOf(radius.getProgress()).concat(" km");
        radiusDistance.setText(text);

        Button button = (Button) view.findViewById(R.id.profile_save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSave();
                showToast(getString(R.string.profile_save_toast_message));
            }
        });

    }

    public void onClickSave() {
        User user = ApplicationServiceFactory.getInstance(getContext()).getFacebookService().getUser();

        user.setName(username.getText().toString());
        user.setPassword(password.getText().toString());
        user.setName(name.getText().toString());
        user.setUsername(surname.getText().toString());
        user.setMail(email.getText().toString());
        user.setReceiveNotifications(notifications.isChecked());
        user.setLocationAddress(address.getText().toString());
        user.setLocationRadius(radius.getProgress());
        
        
    }

    protected void showToast(String message) {
        Toast toast = Toast.makeText(
                getContext(),
                message,
                Toast.LENGTH_LONG);
        toast.show();
    }

    private class DownloadImageWithURLTask extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... urls) {
            String pathToFile = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(pathToFile).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }
        protected void onPostExecute(Bitmap result) {
            profileImage.setImageBitmap(result);
        }
    }

}
