package com.example.aldiansyahramadlan.moviecatalogue_ui_ux.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.CustomOnItemClickListener;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.DetailMovie;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.R;
import com.example.aldiansyahramadlan.moviecatalogue_ui_ux.model.Movies;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.CategoryViewHolder>{

    private Context context;
    private ArrayList<Movies> Dmovies  = new ArrayList<>();

    public MovieAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Movies> getMovies() {
        return Dmovies;
    }

    public void setMovies(ArrayList<Movies> movies) {
        Dmovies.clear();
        Dmovies.addAll(movies);
//        this.Dmovies = movies;
        notifyDataSetChanged();
    }

    private String word;
    public void setWord(String word){
        this.word = word;
    }

    private boolean isFav;
    public boolean isOnFavorite(boolean value){
        this.isFav=value;
        return value;
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new MovieAdapter.CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder categoryViewHolder, final int i) {

        categoryViewHolder.txtName.setText(getMovies().get(i).getTitle());
        categoryViewHolder.txtDescription.setText(getMovies().get(i).getOverview());
        String uri = "https://image.tmdb.org/t/p/original" + getMovies().get(i).getPoster();
//        Glide.with(context)
//                .load(uri)
//                .apply(new RequestOptions().override(55,55))
//                .into(categoryViewHolder.imgPhoto);
        Glide.with(categoryViewHolder.itemView.getContext())
                .load(uri)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        categoryViewHolder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(categoryViewHolder.imgPhoto);

        categoryViewHolder.itemView.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, word + " "+getMovies().get(position).getTitle(), Toast.LENGTH_SHORT).show();
                Intent move = new Intent(context, DetailMovie.class);
                Movies movie = new Movies();
                movie.setTitle(getMovies().get(i).getTitle());
                movie.setPoster(getMovies().get(i).getPoster());
                movie.setOverview(getMovies().get(i).getOverview());
                movie.setOnfavorites(isFav);
                move.putExtra(DetailMovie.EXTRA_MOVIE,movie);
                move.putExtra(DetailMovie.EXTRA_POSITION,getMovies().get(i).getId());

                context.startActivity(move);
            }
        }));

    }

    @Override
    public int getItemCount() {
        return Dmovies.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName;
        private TextView txtDescription;
        private ImageView imgPhoto;
        private ProgressBar progressBar;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_name);
            txtDescription = itemView.findViewById(R.id.txt_description);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
