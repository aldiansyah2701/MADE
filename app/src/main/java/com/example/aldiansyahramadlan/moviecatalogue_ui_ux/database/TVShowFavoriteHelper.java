package com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.TVShow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.TVFavColumns.TV_TABLE_NAME;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.TVFavColumns.TV_TITLE;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.TVFavColumns.TV_OVERVIEW;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.TVFavColumns.TV_PHOTO;

public class TVShowFavoriteHelper {
    private static final String DATABASE_TABLE = TV_TABLE_NAME;
    private static DatabaseHelper databaseHelper;
    private static TVShowFavoriteHelper INSTANCE;

    private static SQLiteDatabase database;

    private TVShowFavoriteHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static TVShowFavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TVShowFavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<TVShow> getAllTvFavorite() {
        ArrayList<TVShow> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        TVShow tvShowFavorite;
        if (cursor.getCount() > 0) {
            do {
                tvShowFavorite = new TVShow();
                tvShowFavorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvShowFavorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TV_TITLE)));
                tvShowFavorite.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(TV_OVERVIEW)));
                tvShowFavorite.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(TV_PHOTO)));

                arrayList.add(tvShowFavorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTv(TVShow tvShowFavorite) {
        ContentValues args = new ContentValues();
        args.put(_ID, tvShowFavorite.getId());
        args.put(TV_TITLE, tvShowFavorite.getTitle());
        args.put(TV_OVERVIEW, tvShowFavorite.getOverview());
        args.put(TV_PHOTO, tvShowFavorite.getPoster());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteTv(int id) {
        return database.delete(TV_TABLE_NAME, _ID + " = '" + id + "'", null);
    }


}
