package com.example.flixster_movies_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster_movies_app.adapters.MovieAdapter;
import com.example.flixster_movies_app.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;


public class MainActivity extends AppCompatActivity {

    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=6513ffdbf170591e210979d73e4e11b1";
    public static final String TAG = "MainActivity";

    RecyclerView rvMovies;
    List<Movie> movies;
    MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();

        movieAdapter = new MovieAdapter(this, movies);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.e(TAG, "OnSuccess");

                JSONObject jsonObject = json.jsonObject;

                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG,"Results: "+results.toString());
                    movies.addAll(Movie.fromJsonArray(results));
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

//API Key: 6513ffdbf170591e210979d73e4e11b1

//https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed
//https://api.themoviedb.org/3/movie/now_playing?api_key=6513ffdbf170591e210979d73e4e11b1