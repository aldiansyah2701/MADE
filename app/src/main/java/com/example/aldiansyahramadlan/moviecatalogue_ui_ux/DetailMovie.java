package com.example.aldiansyahramadlan.moviecatalogue_ui_ux;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailMovie extends AppCompatActivity {

    ImageView photo;
    TextView nameMovie, descriptionMovie, sinopsis, sutradara, tahun;
    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);


        photo = findViewById(R.id.detail_img_photo);
        photo.setImageResource(movie.getPhoto());

        nameMovie = findViewById(R.id.tv_item_name);
        nameMovie.setText(movie.getName());

        descriptionMovie = findViewById(R.id.tv_item_description);
        descriptionMovie.setText(movie.getDescription());

        sinopsis = findViewById(R.id.tv_item_sinopsis);
        sinopsis.setText(movie.getSinopsis());

        sutradara = findViewById(R.id.tv_item_sutradara);
        sutradara.setText(movie.getSutradara());

        tahun = findViewById(R.id.tv_item_tahun);
        tahun.setText(movie.getTahun());


    }
}
