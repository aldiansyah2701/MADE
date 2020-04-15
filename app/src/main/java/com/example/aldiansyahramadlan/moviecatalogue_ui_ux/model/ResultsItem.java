package com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import static android.provider.BaseColumns._ID;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.getColumnInt;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.MovieFavColumns.MOVIE_TITLE;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.MovieFavColumns.MOVIE_OVERVIEW;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.MovieFavColumns.MOVIE_PHOTO;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.DatabaseContract.getColumnString;

public class ResultsItem {
    @SerializedName("overview")
    private String overview;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("id")
    private int id;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public ResultsItem() {
    }

    public ResultsItem(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.title = getColumnString(cursor, MOVIE_TITLE);
        this.posterPath = getColumnString(cursor, MOVIE_PHOTO);
        this.overview = getColumnString(cursor, MOVIE_OVERVIEW);
    }

    @Override
    public String toString() {
        return
                "ResultsItem{" +
                        "overview = '" + overview + '\'' +
                        ",title = '" + title + '\'' +
                        ",poster_path = '" + posterPath + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }

}
