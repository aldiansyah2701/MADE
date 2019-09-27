package com.example.aldiansyahramadlan.moviecatalogue_ui_ux;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.adapter.MovieAdapter;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.adapter.TVAdapter;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.MovieFavoriteHelper;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.TVShowFavoriteHelper;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.Movies;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.TVShow;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ListTVFavorit extends AppCompatActivity {
    private RecyclerView rvCategory;
    private TVShowFavoriteHelper tvShowFavoriteHelper;
    private ArrayList<TVShow> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tvfavorit);

        rvCategory = findViewById(R.id.rv_category_favorite);
        rvCategory.setHasFixedSize(true);

        tvShowFavoriteHelper = TVShowFavoriteHelper.getInstance(getApplicationContext());
        tvShowFavoriteHelper.open();

        WeakReference<TVShowFavoriteHelper> weakReference = new WeakReference<>(tvShowFavoriteHelper);
        ArrayList<TVShow> arrayList = weakReference.get().getAllTvFavorite();

        list.addAll(arrayList);

        rvCategory.setLayoutManager(new LinearLayoutManager(this));
        TVAdapter tvAdapter = new TVAdapter(this);
        tvAdapter.setWord(getResources().getString(R.string.choose));
        tvAdapter.setTV(list);
        tvAdapter.isOnFavorite(true);
        rvCategory.setAdapter(tvAdapter);

    }
}
