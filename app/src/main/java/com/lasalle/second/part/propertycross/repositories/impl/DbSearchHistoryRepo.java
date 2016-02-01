package com.lasalle.second.part.propertycross.repositories.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lasalle.second.part.propertycross.model.PropertySearch;
import com.lasalle.second.part.propertycross.model.SearchHistory;
import com.lasalle.second.part.propertycross.repositories.SearchHistoryRepo;
import com.lasalle.second.part.propertycross.util.DatabaseHandler;
import com.lasalle.second.part.propertycross.util.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

public class DbSearchHistoryRepo implements SearchHistoryRepo {

    private static final  String TABLE_NAME = "searchHistory";

    private static final  String DB_QUERY = "query";
    private static final  String DB_QUERY_NAME = "queryName";
    private static final  String DB_LONGITUDE = "longitude";
    private static final  String DB_LATITUDE = "latitude";
    private static final  String DB_RENT = "rent";
    private static final  String DB_SELL = "sell";
    private static final  String DB_RESULTS = "rawResults";
    private static final  String DB_NUM_RENT_RESULTS = "rentResults";
    private static final  String DB_NUM_SELL_RESULTS = "sellResults";
    private static final  String DB_TIMESTAMP = "timestamp";

    private static final String[] TABLE_COLUMNS = new String[]{DB_QUERY, DB_RENT, DB_SELL, DB_RESULTS,
            DB_NUM_RENT_RESULTS, DB_NUM_SELL_RESULTS, DB_TIMESTAMP};

    private Context context;

    public DbSearchHistoryRepo(Context context) {
        this.context = context;
    }

    @Override
    public boolean addSearchResult(PropertySearch propertySearch, int rentResults, int sellResults,
                                String rawResults) {
        ContentValues searchContentValue = createContentValuesFromSearch(propertySearch, rentResults,
                sellResults, rawResults);
        SQLiteDatabase database = DatabaseHandler.getInstance(context).getWritableDatabase();
        long insertedId = database.insert(TABLE_NAME, null, searchContentValue);
        final boolean success = (insertedId != -1);

        return success;
    }

    @Override
    public SearchHistory getSearchHistoryFromSearch(PropertySearch propertySearch) {
        SQLiteDatabase database = DatabaseHandler.getInstance(context).getWritableDatabase();
        Cursor cursor =  database.query(
                TABLE_NAME,
                TABLE_COLUMNS,
                DB_QUERY + "=?",
                new String[]{getQueryFromSearch(propertySearch)},
                null,
                null,
                "timestamp DESC",
                "1");

        SearchHistory searchHistory =  getSearchHistoryFromCursor(cursor);

        cursor.close();

        return searchHistory;
    }

    @Override
    public List<SearchHistory> getRecentSearches() {
        SQLiteDatabase database = DatabaseHandler.getInstance(context).getWritableDatabase();
        Cursor cursor =  database.query(
                TABLE_NAME,
                TABLE_COLUMNS,
                null,
                null,
                null,
                null,
                "timestamp DESC",
                "5");

        List<SearchHistory> searchHistory =  getAllSearchHistoriesFromCursor(cursor);

        cursor.close();

        return searchHistory;
    }

    protected ContentValues createContentValuesFromSearch(PropertySearch propertySearch, int rentResults,
                                                        int sellResults, String rawResults) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DB_QUERY, getQueryFromSearch(propertySearch));
        contentValues.put(DB_QUERY_NAME, getQueryFromSearch(propertySearch));
        contentValues.put(DB_LATITUDE, propertySearch.getLongitude());
        contentValues.put(DB_LONGITUDE, propertySearch.getLatitude());
        contentValues.put(DB_RENT, propertySearch.isRent());
        contentValues.put(DB_SELL, propertySearch.isSell());
        contentValues.put(DB_RESULTS, rawResults);
        contentValues.put(DB_NUM_RENT_RESULTS, rentResults);
        contentValues.put(DB_NUM_SELL_RESULTS, sellResults);

        final long currentTimestamp = System.currentTimeMillis() / 1000L;
        contentValues.put(DB_TIMESTAMP, currentTimestamp);

        return contentValues;
    }

    protected String getQueryFromSearch(PropertySearch propertySearch) {
        String query = propertySearch.getQuery();

        if(query.isEmpty()) {
            query = propertySearch.getLatitude().toString() + "," + propertySearch.getLongitude().toString();
        }

        return query;
    }

    protected SearchHistory getSearchHistoryFromCursor(Cursor cursor) {
        SearchHistory searchHistory = null;

        if(cursor.getCount() != 0 && cursor.moveToFirst()) {
            searchHistory = new SearchHistory();
            searchHistory.setQuery(cursor.getString(cursor.getColumnIndex(DB_QUERY)));
            searchHistory.setRent(DatabaseUtils.getBooleanValue(cursor, DB_RENT));
            searchHistory.setSell(DatabaseUtils.getBooleanValue(cursor, DB_SELL));
            searchHistory.setRawResults(cursor.getString(cursor.getColumnIndex(DB_RESULTS)));
            searchHistory.setRentResults(cursor.getInt(cursor.getColumnIndex(DB_NUM_RENT_RESULTS)));
            searchHistory.setSellResults(cursor.getInt(cursor.getColumnIndex(DB_NUM_SELL_RESULTS)));
            searchHistory.setTimestamp(cursor.getLong(cursor.getColumnIndex(DB_TIMESTAMP)));
        }

        return searchHistory;
    }

    protected List<SearchHistory> getAllSearchHistoriesFromCursor(Cursor cursor) {
        SearchHistory searchHistory;
        List<SearchHistory> searchHistoryList = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                searchHistory = new SearchHistory();
                searchHistory.setQuery(cursor.getString(cursor.getColumnIndex(DB_QUERY)));
                searchHistory.setRent(DatabaseUtils.getBooleanValue(cursor, DB_RENT));
                searchHistory.setSell(DatabaseUtils.getBooleanValue(cursor, DB_SELL));
                searchHistory.setRawResults(cursor.getString(cursor.getColumnIndex(DB_RESULTS)));
                searchHistory.setRentResults(cursor.getInt(cursor.getColumnIndex(DB_NUM_RENT_RESULTS)));
                searchHistory.setSellResults(cursor.getInt(cursor.getColumnIndex(DB_NUM_SELL_RESULTS)));
                searchHistory.setTimestamp(cursor.getLong(cursor.getColumnIndex(DB_TIMESTAMP)));

                searchHistoryList.add(searchHistory);
            } while(cursor.moveToNext());
        }

        return searchHistoryList;
    }

}
