package com.example.flixster_movies_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster_movies_app.models.Movie;
import com.example.flixster_movies_app.models.Video;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.List;

import okhttp3.Headers;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    public final static String TAG = "MovieTrailerActivity";
    public static final String GET_MOVIE_VIDEOS_URL = "https://api.themoviedb.org/3/movie/%s/videos?api_key=6513ffdbf170591e210979d73e4e11b1&language=en-US";

    Movie movie;
    AsyncHttpClient client;
    YouTubePlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        playerView = findViewById(R.id.player);
        client = new AsyncHttpClient();

        GetMovie();
        GetVideo();

    }

    void GetMovie(){
        movie = Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d(TAG, String.format("Showing details for %s",movie.getTitle()));
    }

    void GetVideo(){
        String url = String.format(GET_MOVIE_VIDEOS_URL, movie.getId());
        Log.d(TAG,url);

        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.e(TAG,"OnSuccess");

                try {
                    JSONArray jsonArray = json.jsonObject.getJSONArray("results");
                    Video video = Video.getValidVideo(jsonArray);

                    if(video != null){
                        PlayYoutubeVideo(video.getKey());
                    }else{
                        Log.d(TAG,"No Youtube video");
                        onBackPressed();
                    }

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

    void PlayYoutubeVideo(String videoId){
        playerView.initialize(getString(R.string.Youtube_API_Key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e(TAG,String.format("Error initializing YouTube player id: %s", videoId));
            }
        });
    }
}