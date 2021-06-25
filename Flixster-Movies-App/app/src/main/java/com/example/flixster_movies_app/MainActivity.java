package com.example.flixster_movies_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster_movies_app.adapters.MovieAdapter;
import com.example.flixster_movies_app.databinding.ActivityMainBinding;
import com.example.flixster_movies_app.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;


/*
*   Todo: Extra user stories
* */

public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=6513ffdbf170591e210979d73e4e11b1";
    public static final String TAG = "MainActivity";

    RecyclerView rvMovies;
    List<Movie> movies;
    MovieAdapter movieAdapter;

    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        movies = new ArrayList<>();
        client = new AsyncHttpClient();

        rvMovies = binding.rvMovies;

        movieAdapter = new MovieAdapter(this, movies);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        GetMovies();
    }

    void GetMovies(){

        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() { // API assync call
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.e(TAG, "OnSuccess");

                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");

                    Log.i(TAG,"Results: "+results.toString());
                    movies.addAll(Movie.fromJsonArray(results)); //Transform all movies to Movie object
                    Log.i(TAG,"Movies: "+ movies.size());

                    movieAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.e(TAG,"Json exception");
                }
            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e(TAG,"OnFailure");
            }
        });
    }
}

