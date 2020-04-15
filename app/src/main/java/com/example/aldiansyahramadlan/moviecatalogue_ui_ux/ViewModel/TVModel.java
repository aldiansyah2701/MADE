package com.example.aldiansyahramadlan.moviecatalogue_ui_ux.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.Movies;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.TVShow;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TVModel extends ViewModel {
    private static final String API_KEY = "5e0981d8c0cd0848bf351ca105a5974f";
    private MutableLiveData<ArrayList<TVShow>> listMovies = new MutableLiveData<>();

    public void setTV() {
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TVShow> listItems = new ArrayList<>();

//        String url = "https://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=5e0981d8c0cd0848bf351ca105a5974f&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray list = responseObject.getJSONArray("results");

                    for (int i = 0; i < list.length(); i++) {
                        JSONObject tv = list.getJSONObject(i);
                        TVShow movieItems = new TVShow(tv);
                        listItems.add(movieItems);
                    }
                    listMovies.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }
    public LiveData<ArrayList<TVShow>> getTVs() {
        return listMovies;
    }
}
