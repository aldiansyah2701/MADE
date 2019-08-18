package com.example.aldiansyahramadlan.moviecatalogue_ui_ux;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.CategoryViewHolder> {
    private Context context;
    private ArrayList<Movie> movies;

    ListAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    private String word;
    public void setWord(String word){
        this.word = word;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie, viewGroup, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, final int i) {
        categoryViewHolder.txtName.setText(getMovies().get(i).getName());
        categoryViewHolder.txtDescription.setText(getMovies().get(i).getDescription());
        categoryViewHolder.imgPhoto.setImageResource(getMovies().get(i).getPhoto());

        categoryViewHolder.itemView.setOnClickListener(new CustomOnItemClickListener(i, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Toast.makeText(context, word + " "+getMovies().get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent move = new Intent(context, DetailMovie.class);
                Movie movie = new Movie();
                movie.setDescription(getMovies().get(i).getDescription());
                movie.setName(getMovies().get(i).getName());
                movie.setPhoto(getMovies().get(i).getPhoto());
                movie.setSinopsis(getMovies().get(i).getSinopsis());
                movie.setSutradara(getMovies().get(i).getSutradara());
                movie.setTahun(getMovies().get(i).getTahun());

                move.putExtra(DetailMovie.EXTRA_MOVIE,movie);
                context.startActivity(move);
            }
        }));

    }

    @Override
    public int getItemCount() {
        return getMovies().size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView txtName;
        private TextView txtDescription;
        private ImageView imgPhoto;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_name);
            txtDescription = itemView.findViewById(R.id.txt_description);
            imgPhoto = itemView.findViewById(R.id.img_photo);
        }
    }
}
