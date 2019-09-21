package com.example.aldiansyahramadlan.moviecatalogue_ui_ux;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.Movies;

public class DetailMovie extends AppCompatActivity {

    ImageView photo;
    TextView nameMovie, sinopsis;
    public static final String EXTRA_MOVIE = "extra_movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        Movies movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        String url = "https://image.tmdb.org/t/p/original" + movie.getPoster();
        photo = findViewById(R.id.detail_img_photo);
//        photo.setImageResource(movie.getPhoto());
        Glide.with(DetailMovie.this).load(url).into(photo);
        Glide.with(DetailMovie.this)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(photo);

        nameMovie = findViewById(R.id.tv_item_name);
        nameMovie.setText(movie.getTitle());

        sinopsis = findViewById(R.id.tv_item_sinopsis);
        sinopsis.setText(movie.getOverview());



    }
}
