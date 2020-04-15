package com.example.aldiansyahramadlan.moviecatalogue_ui_ux;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.ViewModel.MoviesModel;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.ViewModel.SearchModel;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.adapter.MovieAdapter;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.Movies;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.MainActivity.INTENT_SEARCH;
import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.MainActivity.INTENT_TAG;

public class SearchActivity extends Fragment {


    RecyclerView rvCategory;
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;
    Toolbar toolbar;
    private SearchModel searchModel;
    private static ArrayList<Movies> listSearch = new ArrayList<>();
    private String paramSearch="";

    public  SearchActivity(){

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
//
        rvCategory = view.findViewById(R.id.recycler_search);
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCategory.setHasFixedSize(true);
        rvCategory.setAdapter(movieAdapter);
//
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


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String data = bundle.getString(INTENT_SEARCH);
            this.paramSearch = data;
        }else{
            paramSearch="";
        }


        // MainViewModel Instance
        searchModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SearchModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Observer
        searchModel.getMovies().observe(Objects.requireNonNull(getActivity()), getMovies);
//
//        // Display The Items
        searchModel.setSearch(paramSearch);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Unsubscribing The Observer
        searchModel.getMovies().removeObserver(getMovies);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_search, container, false);
    }

}
