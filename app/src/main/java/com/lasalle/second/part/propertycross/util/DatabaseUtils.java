package com.lasalle.second.part.propertycross.util;

import android.database.Cursor;

public class DatabaseUtils {

    public static boolean getBooleanValue(Cursor cursor, String columnName) {
        boolean value = false;

        final int columnIndex = cursor.getColumnIndex(columnName);
        if(columnIndex != -1) {
            final int rowValue = cursor.getInt(columnIndex);
            value = (rowValue != 0);
        }

        return value;
    }

}
