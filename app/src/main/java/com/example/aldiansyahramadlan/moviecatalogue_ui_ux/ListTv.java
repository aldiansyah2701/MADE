package com.example.aldiansyahramadlan.moviecatalogue_ui_ux;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.ViewModel.MoviesModel;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.ViewModel.TVModel;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.adapter.MovieAdapter;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.adapter.TVAdapter;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.Movies;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.TVShow;

import java.util.ArrayList;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListTv extends Fragment {

    private RecyclerView rvCategory;
    private ProgressBar progressBar;

    private TVAdapter tvAdapter;
    private TVModel tvModel;
    public ListTv() {
        // Required empty public constructor
    }

    private Observer<ArrayList<TVShow>> getTv = new Observer<ArrayList<TVShow>>() {
        @Override
        public void onChanged(ArrayList<TVShow> tvItem) {
            if (tvItem != null) {
                tvAdapter.setWord(getResources().getString(R.string.choose));
                tvAdapter.setTV(tvItem);
                progressBar.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvAdapter = new TVAdapter(getActivity());
        progressBar = view.findViewById(R.id.progressBar);

        rvCategory = view.findViewById(R.id.rv_category);
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCategory.setHasFixedSize(true);
        rvCategory.setAdapter(tvAdapter);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.bringToFront();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // MainViewModel Instance
        tvModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TVModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Observer
        tvModel.getTVs().observe(Objects.requireNonNull(getActivity()), getTv);

        // Display The Items
        tvModel.setTV();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // Unsubscribing The Observer
        tvModel.getTVs().removeObserver(getTv);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list_tv, container, false);
        return rootView;
    }




}
