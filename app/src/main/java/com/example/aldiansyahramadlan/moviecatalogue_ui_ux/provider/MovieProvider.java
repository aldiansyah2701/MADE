package com.example.aldiansyahramadlan.moviecatalogue_ui_ux.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract;

import org.jetbrains.annotations.Nullable;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.AUTHORITY;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.CONTENT_URI;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.MovieFavColumns.MOVIE_TABLE_NAME;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseHelper;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.MovieFavoriteHelper;

public class MovieProvider extends ContentProvider {
    private static final int MOVIE = 1;

    private static final int MOVIE_ID = 2;
    private static long inserId = 0;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private MovieFavoriteHelper movieFavoriteHelper;

    static {
        sUriMatcher.addURI(AUTHORITY, MOVIE_TABLE_NAME, MOVIE);

        sUriMatcher.addURI(AUTHORITY, MOVIE_TABLE_NAME+"/#",MOVIE_ID);

    }

    private MovieFavoriteHelper movieHelper;

    @Override
    public boolean onCreate() {
        movieFavoriteHelper = new MovieFavoriteHelper(getContext());
        movieHelper = new MovieFavoriteHelper(getContext());
        movieHelper.open();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch(match){
            case MOVIE:
                cursor = movieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added ;

        switch (sUriMatcher.match(uri)){
            case MOVIE:
                added = movieHelper.insertProvider(values);
                this.inserId = added;
                break;
            default:
                added = 0;
                this.inserId = added;
                break;
        }
        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            movieFavoriteHelper.setResultQuery(added);
        }else{
            movieFavoriteHelper.setResultQuery(added);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }


    public long insertID(){
        return inserId;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int movieDeleted;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIE_ID:
                movieDeleted =  movieHelper.deleteProvider(uri.getLastPathSegment());
                Log.v("MovieDetail1", ""+movieDeleted);
                break;
            default:
                movieDeleted = 0;
                break;
        }

        if (movieDeleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return movieDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int movieUpdated ;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                movieUpdated =  movieHelper.updateProvider(uri.getLastPathSegment(),values);
                break;
            default:
                movieUpdated = 0;
                break;
        }

        if (movieUpdated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return movieUpdated;
    }
}
