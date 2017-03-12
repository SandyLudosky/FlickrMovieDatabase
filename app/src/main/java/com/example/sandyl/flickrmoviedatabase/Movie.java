package com.example.sandyl.flickrmoviedatabase;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by sandyl on 2017-03-11.
 */

public class Movie {

    int _id;
    String title;
    String overview;
    String poster;
    String backdrop;
    String releaseDate;
    String youtubeUrl;
    int rating;

    public Movie(String title, String overview) {
        this.title = title;
        this.overview = overview;
    }

    public Movie(int id, String title, String overview, String poster, String backdrop, int rating, String releaseDate) {
        this._id = id;
        this.title = title;
        this.overview = overview;
        this.poster = poster;
        this.backdrop = backdrop;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }

    public Movie(JSONObject object){
        try {
            this.title = object.getString("name");
            this.overview = object.getString("overview");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}

