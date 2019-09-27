package com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    static final class MovieFavColumns implements BaseColumns {
        static final String MOVIE_TABLE_NAME              = "movie_favorites_1";
        static final String MOVIE_TITLE                   = "movie_title";
        static final String MOVIE_OVERVIEW                = "movie_overview";
        static final String MOVIE_PHOTO                   = "movie_photo";

    }

    static final class TVFavColumns implements BaseColumns {
        static final String TV_TABLE_NAME              = "tv_show_favorites_1";
        static final String TV_TITLE                   = "tv_movie_title";
        static final String TV_OVERVIEW                = "tv_movie_overview";
        static final String TV_PHOTO                   = "tv_movie_photo";


    }
}
