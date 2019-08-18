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
public class ListTv extends Fragment {
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
    private ArrayList<Movie> listTv  = new ArrayList<>();
    public ListTv() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_tv, container, false);
        this.context = getActivity();
        prepare();
        addItem();
        rvCategory = rootView.findViewById(R.id.rv_category);
        rvCategory.setHasFixedSize(true);

        listTv.addAll(movies);

        showRecyclerList();



        return rootView;
    }

    private void prepare() {
        dataName = getResources().getStringArray(R.array.data_name_tv);
        dataDescription = getResources().getStringArray(R.array.data_description_tv);
        dataPhoto = getResources().obtainTypedArray(R.array.data_photo_tv);
        dataSinopsis = getResources().getStringArray(R.array.data_sinopsis_tv);
        dataSutradara = getResources().getStringArray(R.array.data_sutradara_tv);
        dataTahun = getResources().getStringArray(R.array.data_tahun_tv);
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
        listAdapter.setMovies(listTv);
        rvCategory.setAdapter(listAdapter);
    }
}
