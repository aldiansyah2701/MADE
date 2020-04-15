package com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.Movies;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.provider.MovieProvider;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.MovieFavColumns.MOVIE_TABLE_NAME;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.MovieFavColumns.MOVIE_OVERVIEW;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.MovieFavColumns.MOVIE_PHOTO;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.MovieFavColumns.MOVIE_TITLE;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.CONTENT_URI;
public class MovieFavoriteHelper {

    private static final String DATABASE_TABLE = MOVIE_TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static MovieFavoriteHelper INSTANCE;
    private Context context;
    private static SQLiteDatabase database;
    private static MovieProvider movieProvider;

    public MovieFavoriteHelper(Context context) {
        this.context = context;
        dataBaseHelper = new DatabaseHelper(context);
    }

    private long resultQueryExe;
    public void setResultQuery(long result){
        this.resultQueryExe = result;
    }

    public static MovieFavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieFavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }


    public ArrayList<Movies> getAllMoviesFav() {
        ArrayList<Movies> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movies movieFavorite;
        if (cursor.getCount() > 0) {
            do {
                movieFavorite = new Movies();
                movieFavorite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieFavorite.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_TITLE)));
                movieFavorite.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_OVERVIEW)));
                movieFavorite.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_PHOTO)));

                arrayList.add(movieFavorite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(Movies movieFavorite) {
        ContentValues args = new ContentValues();
        args.put(_ID, movieFavorite.getId());
        args.put(MOVIE_TITLE, movieFavorite.getTitle());
        args.put(MOVIE_OVERVIEW, movieFavorite.getOverview());
        args.put(MOVIE_PHOTO, movieFavorite.getPoster());

        Uri response = context.getContentResolver().insert(CONTENT_URI, args);
        String last = response.getLastPathSegment();
//        return database.insert(DATABASE_TABLE, null, args);
        return Long.parseLong(last);
    }

    public long deleteMovie(int id) {

        Uri uri = CONTENT_URI;
        uri = uri.buildUpon().appendPath(String.valueOf(id)).build();
//        return database.delete(MOVIE_TABLE_NAME, _ID + " = '" + id + "'", null);
        return context.getContentResolver().delete(uri, null, null);
    }

    public Cursor queryProvider() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,_ID+ " DESC"
        );
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values,_ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }

}
