package com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.MovieFavColumns;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.TVFavColumns;

import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.MovieFavColumns.MOVIE_TABLE_NAME;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.TVFavColumns.TV_TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moviecatalogues1";

    private static final int DATABASE_VERSION = 3;

    private static final String SQL_CREATE_TABLE_MOVIE_FAVORITE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            MovieFavColumns.MOVIE_TABLE_NAME,
            MovieFavColumns._ID,
            MovieFavColumns.MOVIE_TITLE,
            MovieFavColumns.MOVIE_OVERVIEW,
            MovieFavColumns.MOVIE_PHOTO
    );

    private static final String SQL_CREATE_TABLE_TV_SHOW_FAVORITE = String.format(
                    " CREATE TABLE %s" +
                    " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            TVFavColumns.TV_TABLE_NAME,
            TVFavColumns._ID,
            TVFavColumns.TV_TITLE,
            TVFavColumns.TV_OVERVIEW,
            TVFavColumns.TV_PHOTO
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE_FAVORITE);
        db.execSQL(SQL_CREATE_TABLE_TV_SHOW_FAVORITE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TV_TABLE_NAME);
        onCreate(db);
    }
}
