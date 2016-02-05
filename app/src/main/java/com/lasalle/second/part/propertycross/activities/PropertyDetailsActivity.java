package com.lasalle.second.part.propertycross.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.adapters.DetailsCommentsListAdapter;
import com.lasalle.second.part.propertycross.model.Property;
import com.lasalle.second.part.propertycross.services.ApplicationServiceFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by eduard on 05/02/2016.
 */
public class PropertyDetailsActivity extends AppCompatActivity implements EditText.OnEditorActionListener{

    public static final int TAKE_PHOTO = 2727;

    private DetailsCommentsListAdapter detailsCommentsListAdapter;
    private Property property;
    private Uri photoLocation;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 13;
    private String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_property_details);
        property = ApplicationServiceFactory.getInstance(getApplicationContext())
                .getPropertyService().getLastDetail();

        setInfo();

        EditText editText = (EditText) findViewById(R.id.details_comment_input);
        editText.setOnEditorActionListener(this);

        detailsCommentsListAdapter = new DetailsCommentsListAdapter(this, property.getComments());
        ListView listView = (ListView) findViewById(R.id.details_comments_list);
        listView.setAdapter(detailsCommentsListAdapter);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    private void setInfo() {
        setPhoto();
        setLastChecked();
        setPrice();
        setDescription();
    }

    private void setPhoto() {
        ImageView imageView = (ImageView) findViewById(R.id.details_photoTitle);
        switch ((int) (Math.random() % 4)) {
            case 0:
                imageView.setImageResource(R.drawable.flat_sample_image);
                break;
            case 1:
                imageView.setImageResource(R.drawable.flat_sample_image_2);
                break;
            case 2:
                imageView.setImageResource(R.drawable.flat_sample_image_3);
            case 3:
                imageView.setImageResource(R.drawable.flat_sample_image_4);
                break;
        }
    }

    private void setLastChecked() {
        TextView lastChecked = (TextView) findViewById(R.id.details_last_viewed_label);
        String format = "dd.MM.yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        CharSequence text = getString(R.string.details_lastchecked_on_text)
                .concat(sdf.format(property.getLastQuery()));

        lastChecked.setText(text);
    }

    private void setPrice() {
        List<Object> objectList = new ArrayList<>();
        objectList.add(property.getPrice());

        String formattedString = String.format(getString(R.string.results_price),
                objectList.toArray());

        TextView price = (TextView) findViewById(R.id.details_price_label);
        price.setText(formattedString);
    }

    private void setDescription() {
        TextView description = (TextView) findViewById(R.id.details_description_label);
        description.setText(property.getDescription());
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean handled = false;

        final boolean isKeyboardEnter = (event != null) &&
                (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) &&
                (event.getAction() == KeyEvent.ACTION_DOWN);


        if ((actionId == EditorInfo.IME_ACTION_SEARCH) || isKeyboardEnter) {
            handled = true;

            hideKeyboard(v);

            String comment = v.getText().toString();
            v.setText("");

            ImageView imageView = (ImageView) findViewById(R.id.details_new_photo_view);
            imageView.setVisibility(View.GONE);

            detailsCommentsListAdapter.addComments(comment, photoLocation);
            detailsCommentsListAdapter.notifyDataSetChanged();

            photoLocation = null;
        }
        return handled;
    }

    private void hideKeyboard(View view) {
        view.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void CameraClick(View v) {
        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        long code = Calendar.getInstance().getTimeInMillis();

        verifyStoragePermissions(this);

        File file = new File(Environment.getExternalStorageDirectory(),
                code+".jpg");
        photoLocation = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoLocation);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PHOTO && resultCode==RESULT_OK){
            ImageView photo = (ImageView) findViewById(R.id.details_new_photo_view);
            Log.d("PHOTO", photoLocation.toString());
            photo.setImageURI(photoLocation);
            photo.setVisibility(View.VISIBLE);
        }
    }

    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
