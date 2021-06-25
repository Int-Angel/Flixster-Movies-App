package com.example.flixster_movies_app.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Video {
    public final static String Youtube = "YouTube";
    String key;
    String site;

    public Video(JSONObject jsonObject) throws JSONException {
        key = jsonObject.getString("key");
        site = jsonObject.getString("site");
    }

    public String getKey() {
        return key;
    }

    public String getSite() {
        return site;
    }

    public static Video getValidVideo(JSONArray jsonArray) throws JSONException {
        for(int i = 0; i<jsonArray.length(); i++){
            Video video = new Video(jsonArray.getJSONObject(i));

            if(video.site.equals(Youtube))
                return video;
        }
        return null;
    }

    public static List<Video> fromJsonArray(JSONArray jsonArray) throws JSONException {
        List<Video> videos = new ArrayList<>();

        for(int i = 0; i<jsonArray.length(); i++){
            videos.add(new Video(jsonArray.getJSONObject(i)));
        }

        return videos;
    }
}
