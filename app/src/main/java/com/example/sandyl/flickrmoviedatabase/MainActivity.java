package com.example.sandyl.flickrmoviedatabase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> movies = new ArrayList<String>();
    ArrayAdapter<String> movieAdapter;
    ListView listView;
    String apiKey = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+apiKey+"";

        listView = (ListView) findViewById(R.id.listView);
        movieAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, movies);
        listView.setAdapter(movieAdapter);

        //fetching all movies with api key
        getMovies(url);
        movieAdapter.notifyDataSetChanged();
    }

    public void getMovies(String url) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        final ArrayList<String> movieList = new ArrayList<String>();

        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.v("debug", "failure");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject jsonObject) {
                Log.v("debug", "success");

                int i;
                try {
                    JSONArray jArray = jsonObject.getJSONArray("results");

                    if (jArray != null) {

                        for (i=0;i<jArray.length();i++){
                            int id = jArray.optJSONObject(i).getInt("id");
                            String title = jArray.optJSONObject(i).getString("title");
                            String overview = jArray.optJSONObject(i).getString("overview");
                            String image = jArray.optJSONObject(i).getString("poster_path");
                            String backdrop = jArray.optJSONObject(i).getString("backdrop_path");
                            String release = jArray.optJSONObject(i).getString("release_date");
                            int rating = jArray.optJSONObject(i).getInt("vote_average");

                            Movie movie = new Movie(id, title, overview, image, backdrop, rating, release);

                            movies.add(movie.title);
                        }

                        movieAdapter.addAll(movies);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
