package com.example.aldiansyahramadlan.moviecatalogue_ui_ux;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.ViewModel.MoviesModel;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.adapter.MovieAdapter;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.MovieFavoriteHelper;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.Movies;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListMovie extends Fragment {

    private RecyclerView rvCategory;
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;
    private MoviesModel moviesModel;
    public ListMovie() {
        // Required empty public constructor
    }

    private Observer<ArrayList<Movies>> getMovies = new Observer<ArrayList<Movies>>() {
        @Override
        public void onChanged(ArrayList<Movies> moviesItem) {
            if (moviesItem != null) {
                movieAdapter.setWord(getResources().getString(R.string.choose));
                movieAdapter.setMovies(moviesItem);
                movieAdapter.isOnFavorite(false);
                progressBar.setVisibility(View.GONE);
            }

//            showLoading(false);

        }
    };


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieAdapter = new MovieAdapter(getActivity());
        progressBar = view.findViewById(R.id.progressBar);

        rvCategory = view.findViewById(R.id.rv_category);
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCategory.setHasFixedSize(true);
        rvCategory.setAdapter(movieAdapter);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.bringToFront();

//        AppCompatImageButton fav = view.findViewById(R.id.fav);
//        fav.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(),  "LOVE", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // MainViewModel Instance
            moviesModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MoviesModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Observer
        moviesModel.getMovies().observe(Objects.requireNonNull(getActivity()), getMovies);

        // Display The Items
        moviesModel.setMovies();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Unsubscribing The Observer
        moviesModel.getMovies().removeObserver(getMovies);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_movie, container, false);
    }




    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
