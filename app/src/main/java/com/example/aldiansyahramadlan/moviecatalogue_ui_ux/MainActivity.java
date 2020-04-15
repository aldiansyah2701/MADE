package com.example.aldiansyahramadlan.moviecatalogue_ui_ux;

import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.notification.SettingMenu;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String INTENT_SEARCH = "intent_search";
    public static final String INTENT_TAG = "tag";
    BottomNavigationView navigation;
    private int flagMenuFav = 1;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {



        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.navigation_movie:

                    fragment = new ListMovie();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    flagMenuFav = 1;
                    return true;
                case R.id.navigation_tv:

                    fragment = new ListTv();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    flagMenuFav = 2;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.navigation_movie);
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return super.onCreateOptionsMenu(menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//
                Fragment fragment;
                fragment = new SearchActivity();

                Bundle bundle = new Bundle();
                bundle.putString(INTENT_SEARCH,query);
                fragment.setArguments(bundle);


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                        .commit();

//                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
//                intent.putExtra(INTENT_SEARCH, query);
//                intent.putExtra(INTENT_TAG, "search");
//                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
//            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            Intent mIntent = new Intent(this, SettingMenu.class);
            startActivity(mIntent);
        }else if(item.getItemId() == R.id.love){

            Intent intent = new Intent(this, ListMovieFavorit.class);
            startActivity(intent);
            Toast.makeText(this,  "LOVE", Toast.LENGTH_SHORT).show();

//            if(flagMenuFav==1){
//                Intent intent = new Intent(this, ListMovieFavorit.class);
//                startActivity(intent);
//                Toast.makeText(this,  "LOVE 1", Toast.LENGTH_SHORT).show();
//            }else if(flagMenuFav==2){
////                Intent intent = new Intent(this, ListTVFavorit.class);
//                Intent intent = new Intent(this, ListMovieFavorit.class);
//                startActivity(intent);
//                Toast.makeText(this,  "LOVE 2", Toast.LENGTH_SHORT).show();
//            }

        }
        return super.onOptionsItemSelected(item);
    }


}
