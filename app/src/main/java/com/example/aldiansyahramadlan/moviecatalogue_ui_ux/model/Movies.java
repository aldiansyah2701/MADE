package com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Movies implements Parcelable {
    private int id;
    private String title;
    private String released;
    private String overview;
    private String poster;


    public Movies(){

    }

    public Movies(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String released = object.getString("release_date");
            String overview = object.getString("overview");
            String poster = object.getString("poster_path");

            this.id = id;
            this.title = title;
            this.released = released;
            this.overview = overview;
            this.poster = poster;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }


    public String getOverview() {
        return overview;
    }

    public String getPoster() {
        return poster;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.released);
        dest.writeString(this.overview);
        dest.writeString(this.poster);

    }

    private Movies(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.released = in.readString();
        this.overview = in.readString();
        this.poster = in.readString();

    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
