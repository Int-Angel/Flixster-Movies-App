package com.example.flixster_movies_app.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.Target;
import com.example.flixster_movies_app.MovieDetailsActivity;
import com.example.flixster_movies_app.R;
import com.example.flixster_movies_app.databinding.ItemMovieBinding;
import com.example.flixster_movies_app.models.Movie;


import org.parceler.Parcels;

import java.util.List;


public class MovieAdapter extends  RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    public static final String TAG = "MoviesAdapter";
    Context context;
    List<Movie> movies;


    public MovieAdapter(Context context, List<Movie> movies){
        this.movies = movies;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG,"onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder " + position);
        holder.Bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount");
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        ImageView ivPoster;
        TextView tvTitle;
        TextView tvOverview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //ItemMovieBinding binding = ItemMovieBinding.inflate(getLayoutInflater());

            ivPoster = itemView.findViewById(R.id.ivPoster);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);

            itemView.setOnClickListener(this);
        }

        public void Bind(Movie movie){
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());

            String imageUrl;
            int placeholderId;

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPosterPath();
                placeholderId = R.drawable.flicks_backdrop_placeholder;
            }else{
                imageUrl = movie.getPosterPath();
                placeholderId = R.drawable.flicks_movie_placeholder;
            }

            int radius = 10;
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(placeholderId)
                    .error(placeholderId)
                    .transform(new RoundedCorners(radius))
                    .into(ivPoster);
        }

        void OpenMovieDetails(){
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                Movie movie = movies.get(position);
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }
        }

        @Override
        public void onClick(View v) {
            OpenMovieDetails();
        }
    }
}
