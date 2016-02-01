package com.lasalle.second.part.propertycross.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lasalle.second.part.propertycross.R;
import com.lasalle.second.part.propertycross.model.Property;

import java.util.ArrayList;
import java.util.List;

public class SearchResultListAdapter extends BaseAdapter {

    private LayoutInflater layoutInflater;
    private List<Property> propertyList;
    private Activity ownerActivity;

    public SearchResultListAdapter(Activity ownerActivity, List<Property> propertyList) {
        layoutInflater = ownerActivity.getLayoutInflater();
        this.propertyList = propertyList;
        this.ownerActivity = ownerActivity;
    }

    @Override
    public int getCount() {
        return propertyList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.search_result_item, parent, false);
        }

        Property property = propertyList.get(position);

        setViewImage(convertView);
        setViewTitle(convertView, property);
        setViewSubtitle(convertView, property);
        setViewPrice(convertView, property);

        return convertView;
    }

    protected void setViewImage(View rowView) {
        ImageView imageView = (ImageView) rowView.findViewById(R.id.search_result_image);
        if(Math.random() % 2 == 0) {
            imageView.setImageResource(R.drawable.flat_sample_image);
        }
        else {
            imageView.setImageResource(R.drawable.flat_sample_image_2);
        }
    }

    protected void setViewTitle(View rowView, Property property) {
        TextView titleTextView = (TextView) rowView.findViewById(R.id.search_result_title);
        titleTextView.setText(property.getName());
    }

    protected void setViewSubtitle(View rowView, Property property) {
        TextView subtitleTextView = (TextView) rowView.findViewById(R.id.search_result_subtitle);

        List<Object> objectList = new ArrayList<>();
        objectList.add(property.getSquareFootage());
        String formattedString;
        if (property.isRent()) {
            formattedString = String.format(ownerActivity.getString(R.string.results_subtitle_text_rent),
                    objectList.toArray());
        } else {
            formattedString = String.format(ownerActivity.getString(R.string.results_subtitle_text_sale),
                    objectList.toArray());
        }

        subtitleTextView.setText(formattedString);
    }

    protected void setViewPrice(View rowView, Property property) {
        TextView subtitleTextView = (TextView) rowView.findViewById(R.id.search_result_price);

        List<Object> objectList = new ArrayList<>();
        objectList.add(property.getPrice());

        String formattedString = String.format(ownerActivity.getString(R.string.results_price),
                objectList.toArray());
        subtitleTextView.setText(formattedString);
    }
}
