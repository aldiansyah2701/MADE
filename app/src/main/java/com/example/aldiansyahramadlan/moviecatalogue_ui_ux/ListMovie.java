package com.example.aldiansyahramadlan.moviecatalogue_ui_ux;


import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListMovie extends Fragment {
    private String[] dataName;
    private String[] dataDescription;
    private String[] dataSinopsis;
    private String[] dataSutradara;
    private String[] dataTahun;
    private TypedArray dataPhoto;
    private MovieAdapter adapter;
    private ArrayList<Movie> movies;
    private Context context;
    private RecyclerView rvCategory;

    private ArrayList<Movie> listMovies  = new ArrayList<>();
    public ListMovie() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_list_movie, container, false);
        prepare();
        addItem();

        this.context = getActivity();

        rvCategory = rootView.findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);

        listMovies.addAll(movies);

        showRecyclerList();

        return rootView;
    }

    private void prepare() {
        dataName = getResources().getStringArray(R.array.data_name_movie);
        dataDescription = getResources().getStringArray(R.array.data_description_movie);
        dataPhoto = getResources().obtainTypedArray(R.array.data_photo_movie);
        dataSinopsis = getResources().getStringArray(R.array.data_sinopsis_movie);
        dataSutradara = getResources().getStringArray(R.array.data_sutradara_movie);
        dataTahun = getResources().getStringArray(R.array.data_tahun_movie);
    }

    private void addItem() {
        movies = new ArrayList<>();

        for (int i = 0; i < dataName.length; i++) {
            Movie movie = new Movie();
            movie.setPhoto(dataPhoto.getResourceId(i, -1));
            movie.setName(dataName[i]);
            movie.setDescription(dataDescription[i]);
            movie.setSinopsis(dataSinopsis[i]);
            movie.setSutradara(dataSutradara[i]);
            movie.setTahun(dataTahun[i]);
            movies.add(movie);
        }


    }

    private void showRecyclerList() {
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        ListAdapter listAdapter = new ListAdapter(getActivity());
        listAdapter.setMovies(listMovies);
        listAdapter.setWord(getResources().getString(R.string.choose));
        rvCategory.setAdapter(listAdapter);
    }

}
