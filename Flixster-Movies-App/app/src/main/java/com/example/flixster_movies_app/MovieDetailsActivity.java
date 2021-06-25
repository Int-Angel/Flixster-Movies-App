package com.example.flixster_movies_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flixster_movies_app.databinding.ActivityMovieDetailsBinding;
import com.example.flixster_movies_app.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {

    public static final String TAG = "MovieDetailsActivity";
    Movie movie;

    TextView tvTitle;
    TextView tvOverview;
    TextView tvVoteAverage;
    RatingBar rbVoteAverage;
    ImageView ivPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_movie_details);

        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        tvTitle = binding.tvTitle;
        tvOverview = binding.tvOverview;
        rbVoteAverage = binding.rbVoteAverage;
        ivPoster = binding.ivMoviePosterDetails;
        tvVoteAverage = binding.tvVoteAverage;

        GetMovie();
        ShowMovie();
        MovieTrailer();
    }

    void GetMovie(){
        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d(TAG, String.format("Showing details for %s",movie.getTitle()));
    }

    void ShowMovie(){
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        float rating = (float)movie.getVoteAverage();
        rbVoteAverage.setRating(rating);

        tvVoteAverage.setText(rating+"/10");

        Glide.with(this)
                .load(movie.getBackdropPosterPath())
                .placeholder(R.drawable.flicks_movie_placeholder)
                .error(R.drawable.flicks_movie_placeholder)
                .into(ivPoster);
    }

    void MovieTrailer(){
        ivPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenMovieTrailer();
            }
        });
    }

    void OpenMovieTrailer(){
        Intent intent = new Intent(this, MovieTrailerActivity.class);
        intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
        startActivity(intent);
    }
}