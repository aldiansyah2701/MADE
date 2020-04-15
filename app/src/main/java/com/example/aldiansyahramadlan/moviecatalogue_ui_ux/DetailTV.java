package com.example.aldiansyahramadlan.moviecatalogue_ui_ux;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.MovieFavoriteHelper;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.database.TVShowFavoriteHelper;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.Movies;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.TVShow;

import static com.example.aldiansyahramadlan.moviecatalogue_ui_ux.DetailMovie.EXTRA_MOVIE_FAVORITE;

public class DetailTV extends AppCompatActivity implements View.OnClickListener {

    ImageView photo;
    TextView nameMovie, sinopsis;
    String urlPhoto;
    private int flag, position;
    private boolean isFav = false;
    public static final int RESULT_ADD = 101;

    private TVShowFavoriteHelper tvShowFavoriteHelper;
    private MovieFavoriteHelper movieFavoriteHelper;

    public static final String EXTRA_TV= "extra_tv";
    Button btnSaveTV, btnDeleteMovie;

    public static final String EXTRA_TV_FAVORITE    = "extra_tv_favorite";
    public static final String EXTRA_POSITION = "extra_position";

    private TVShow tvShowFavorite;
    private Movies movieFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        TVShow tv = getIntent().getParcelableExtra(EXTRA_TV);
        position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        String url = "https://image.tmdb.org/t/p/original" + tv.getPoster();
        urlPhoto = tv.getPoster();
        photo = findViewById(R.id.detail_img_photo);
//        photo.setImageResource(movie.getPhoto());
        Glide.with(DetailTV.this).load(url).into(photo);
        Glide.with(DetailTV.this)
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
        nameMovie.setText(tv.getTitle());
        sinopsis = findViewById(R.id.tv_item_sinopsis);
        sinopsis.setText(tv.getOverview());

        btnSaveTV    = findViewById(R.id.btn_submit);
        btnSaveTV.setOnClickListener(this);

        btnDeleteMovie = findViewById(R.id.btn_delete);
        btnDeleteMovie.setOnClickListener(this);

//        tvShowFavoriteHelper = TVShowFavoriteHelper.getInstance(getApplicationContext());
//        tvShowFavoriteHelper.open();

        movieFavoriteHelper = MovieFavoriteHelper.getInstance(getApplicationContext());
        movieFavoriteHelper.open();

//        tvShowFavorite = getIntent().getParcelableExtra(EXTRA_TV_FAVORITE);
        movieFavorite = getIntent().getParcelableExtra(EXTRA_MOVIE_FAVORITE);
        if (tv.isOnfavorites()) {
            flag = getIntent().getIntExtra(EXTRA_POSITION, 0);
            isFav = true;
            btnSaveTV.setVisibility(View.GONE);

        } else {
//            tvShowFavorite = new TVShow();
            movieFavorite = new Movies();
            btnDeleteMovie.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {



        if (view.getId() == R.id.btn_submit) {
            String title        = nameMovie.getText().toString().trim();
            String overview     = sinopsis.getText().toString().trim();
            String url_image    = urlPhoto.trim();

//            tvShowFavorite.setId(position);
//            tvShowFavorite.setTitle(title);
//            tvShowFavorite.setOverview(overview);
//            tvShowFavorite.setPoster(url_image);

            movieFavorite.setId(position);
            movieFavorite.setTitle(title);
            movieFavorite.setOverview(overview);
            movieFavorite.setPoster(url_image);

            Intent intent = new Intent();
//            intent.putExtra(EXTRA_TV_FAVORITE, tvShowFavorite);
//            intent.putExtra(EXTRA_POSITION, flag);

            intent.putExtra(EXTRA_MOVIE_FAVORITE, movieFavorite);
            intent.putExtra(EXTRA_POSITION, flag);

            if (!isFav) {
                long result = movieFavoriteHelper.insertMovie(movieFavorite);
//                long result = tvShowFavoriteHelper.insertTv(tvShowFavorite);

                if (result > 0) {
//                    tvShowFavorite.setId((int) result);
                    movieFavorite.setId((int) result);
                    setResult(RESULT_ADD, intent);
                    Toast.makeText(DetailTV.this, getString(R.string.succes_add_data), Toast.LENGTH_SHORT).show();
//                    finish();
                    intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DetailTV.this, getString(R.string.failed_add_data), Toast.LENGTH_SHORT).show();
                }
            }

        }else if(view.getId() == R.id.btn_delete){
//            tvShowFavoriteHelper = TVShowFavoriteHelper.getInstance(getApplicationContext());
//            long result = tvShowFavoriteHelper.deleteTv(position);

            movieFavoriteHelper = MovieFavoriteHelper.getInstance(getApplicationContext());
            long result = movieFavoriteHelper.deleteMovie(position);

            if (result > 0) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(DetailTV.this, getString(R.string.notif_failed_delete), Toast.LENGTH_SHORT).show();
            }

        }

    }
}
