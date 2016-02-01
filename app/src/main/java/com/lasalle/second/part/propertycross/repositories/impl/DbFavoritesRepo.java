package com.lasalle.second.part.propertycross.repositories.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lasalle.second.part.propertycross.model.Property;
import com.lasalle.second.part.propertycross.repositories.FavoritesRepo;
import com.lasalle.second.part.propertycross.util.DatabaseHandler;
import com.lasalle.second.part.propertycross.util.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduard on 24/01/2016.
 */
public class DbFavoritesRepo implements FavoritesRepo {

    private static final  String TABLE_NAME = "favorite";

    private static final  String DB_NAME = "name";
    private static final  String DB_RENT = "rent";
    private static final  String DB_CITY_NAME = "cityName";
    private static final  String DB_POSTAL_CODE = "postalCode";
    private static final  String DB_DESCRIPTION = "description";
    private static final  String DB_ADDRESS = "address";
    private static final  String DB_OWNER_MAIL = "ownerMail";
    private static final  String DB_LATITUDE = "latitude";
    private static final  String DB_LONGITUDE = "longitude";
    private static final  String DB_SQUARE_FOOTAGE = "squareFootage";
    private static final  String DB_PRICE = "price";
    private static final  String DB_OWNER_PHONE_NUMBER = "ownerPhoneNumber";

    private static final String[] TABLE_COLUMNS = new String[]{DB_NAME, DB_RENT, DB_CITY_NAME,
        DB_POSTAL_CODE, DB_DESCRIPTION, DB_ADDRESS, DB_OWNER_MAIL, DB_LATITUDE, DB_LONGITUDE,
        DB_SQUARE_FOOTAGE, DB_PRICE, DB_OWNER_PHONE_NUMBER};

    private Context context;

    public DbFavoritesRepo(Context context) {
        this.context = context;
    }


    @Override
    public List<Property> getFavoriteProperties() {
        SQLiteDatabase database = DatabaseHandler.getInstance(context).getWritableDatabase();
        Cursor cursor = database.query(
                TABLE_NAME,
                TABLE_COLUMNS,
                null,
                null,
                null,
                null,
                null,
                null);

        List<Property> propertyList =  getAllPropertiesFromCursor(cursor);

        cursor.close();

        return propertyList;
    }

    @Override
    public boolean addFavorite(Property property) {
        ContentValues searchContentValue = createContentValuesFromProperty(property);
        SQLiteDatabase database = DatabaseHandler.getInstance(context).getWritableDatabase();
        long insertedId = database.insert(TABLE_NAME, null, searchContentValue);
        final boolean success = (insertedId != -1);

        return success;
    }

    protected ContentValues createContentValuesFromProperty(Property property) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DB_NAME, property.getName());
        contentValues.put(DB_RENT, property.isRent());
        contentValues.put(DB_CITY_NAME, property.getCityName());
        contentValues.put(DB_POSTAL_CODE, property.getPostalCode());
        contentValues.put(DB_DESCRIPTION, property.getDescription());
        contentValues.put(DB_ADDRESS, property.getAddress());
        contentValues.put(DB_OWNER_MAIL, property.getOwnerMail());
        contentValues.put(DB_LATITUDE, property.getLatitude());
        contentValues.put(DB_LONGITUDE, property.getLongitude());
        contentValues.put(DB_SQUARE_FOOTAGE, property.getSquareFootage());
        contentValues.put(DB_PRICE, property.getPrice());
        contentValues.put(DB_OWNER_PHONE_NUMBER, property.getOwnerPhoneNumber());

        return contentValues;
    }


    protected List<Property> getAllPropertiesFromCursor(Cursor cursor) {
        Property property;
        List<Property> propertyList = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                property = new Property();
                property.setName((cursor.getString(cursor.getColumnIndex(DB_NAME))));
                property.setRent(DatabaseUtils.getBooleanValue(cursor, DB_RENT));
                property.setCityName(cursor.getString(cursor.getColumnIndex(DB_CITY_NAME)));
                property.setPostalCode(cursor.getString(cursor.getColumnIndex(DB_POSTAL_CODE)));
                property.setDescription(cursor.getString(cursor.getColumnIndex(DB_DESCRIPTION)));
                property.setAddress(cursor.getString(cursor.getColumnIndex(DB_ADDRESS)));
                property.setOwnerMail(cursor.getString(cursor.getColumnIndex(DB_OWNER_MAIL)));
                property.setLatitude(cursor.getFloat(cursor.getColumnIndex(DB_LATITUDE)));
                property.setLongitude(cursor.getFloat(cursor.getColumnIndex(DB_LONGITUDE)));
                property.setSquareFootage(cursor.getFloat(cursor.getColumnIndex(DB_SQUARE_FOOTAGE)));
                property.setPrice(cursor.getFloat(cursor.getColumnIndex(DB_PRICE)));
                property.setOwnerPhoneNumber(cursor.getString(cursor.getColumnIndex(DB_OWNER_PHONE_NUMBER)));

                propertyList.add(property);
            } while(cursor.moveToNext());
        }

        return propertyList;
    }
}