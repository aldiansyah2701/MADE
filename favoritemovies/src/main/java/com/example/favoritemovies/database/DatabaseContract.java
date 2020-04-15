package com.example.favoritemovies.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String MOVIE_TABLE_NAME = "movie_favorites_1";

    public static final class MovieColumns implements BaseColumns {

        public static String MOVIE_TITLE                   = "movie_title";
        public static String MOVIE_OVERVIEW                = "movie_overview";
        public static String MOVIE_PHOTO                   = "movie_photo";
    }

    public static final String AUTHORITY = "com.example.aldiansyahramadlan.moviecatalogue_ui_ux";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(MOVIE_TABLE_NAME)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }
    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }
    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }
}
