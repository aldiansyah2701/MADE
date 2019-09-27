package com.example.aldiansyahramadlan.moviecatalogue_ui_ux;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.adapter.MovieAdapter;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.MovieFavoriteHelper;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.Movies;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ListMovieFavorit extends AppCompatActivity {
    private RecyclerView rvCategory;
    private MovieFavoriteHelper movieFavoriteHelper;
    private ArrayList<Movies> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_movie_favorit);

        rvCategory = findViewById(R.id.rv_category_favorite);
        rvCategory.setHasFixedSize(true);

        movieFavoriteHelper = MovieFavoriteHelper.getInstance(getApplicationContext());
        movieFavoriteHelper.open();


        WeakReference<MovieFavoriteHelper> weakReference = new WeakReference<>(movieFavoriteHelper);
        ArrayList<Movies> arrayList = weakReference.get().getAllMoviesFav();

        list.addAll(arrayList);

        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        MovieAdapter movieAdapter = new MovieAdapter(this);
        movieAdapter.setWord(getResources().getString(R.string.choose));
        movieAdapter.setMovies(list);
        movieAdapter.isOnFavorite(true);
        rvCategory.setAdapter(movieAdapter);

    }
}
