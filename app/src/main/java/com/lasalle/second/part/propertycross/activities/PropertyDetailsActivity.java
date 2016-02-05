package com.lasalle.second.part.propertycross.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.adapters.DetailsCommentsListAdapter;
import com.lasalle.second.part.propertycross.model.Property;
import com.lasalle.second.part.propertycross.services.ApplicationServiceFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by eduard on 05/02/2016.
 */
public class PropertyDetailsActivity extends AppCompatActivity {

    private DetailsCommentsListAdapter detailsCommentsListAdapter;
    private Property property;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_property_details);
        property = ApplicationServiceFactory.getInstance(getApplicationContext())
                .getPropertyService().getLastDetail();

        setInfo();
        detailsCommentsListAdapter = new DetailsCommentsListAdapter(this, property.getComments());
        ListView listView = (ListView) findViewById(R.id.details_comments_list);
        listView.setAdapter(detailsCommentsListAdapter);
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

}
