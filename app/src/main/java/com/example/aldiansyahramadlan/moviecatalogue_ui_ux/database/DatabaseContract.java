package com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.MovieFavColumns.MOVIE_TABLE_NAME;

public class DatabaseContract {
    public static final class MovieFavColumns implements BaseColumns {
        public static final String MOVIE_TABLE_NAME              = "movie_favorites_1";
        public static final String MOVIE_TITLE                   = "movie_title";
        public static final String MOVIE_OVERVIEW                = "movie_overview";
        public static final String MOVIE_PHOTO                   = "movie_photo";

    }

    static final class TVFavColumns implements BaseColumns {
        static final String TV_TABLE_NAME              = "tv_show_favorites_1";
        static final String TV_TITLE                   = "tv_movie_title";
        static final String TV_OVERVIEW                = "tv_movie_overview";
        static final String TV_PHOTO                   = "tv_movie_photo";


    }

    public static final String AUTHORITY = "com.example.aldiansyahramadlan.moviecatalogue_ui_ux";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(MOVIE_TABLE_NAME)
            .build();

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }
}
