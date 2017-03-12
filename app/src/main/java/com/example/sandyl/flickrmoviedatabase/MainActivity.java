package com.example.sandyl.flickrmoviedatabase;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    ArrayList<Movie> movies = new ArrayList<Movie>();
    MovieAdapter movieAdapter;
    ListView listView;
    String apiKey = "a07e22bc18f5cb106bfe4cc1f83ad8ed";
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Flickr");

            String url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+apiKey+"";

        listView = (ListView) findViewById(R.id.listView);
        movieAdapter = new MovieAdapter(this, movies);
        listView.setAdapter(movieAdapter);

        //fetching all movies with api key
        getMovies(url);
      //  sort(movies);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //startMovieActivity(position);
                showMovieFragment(position);
            }
        });

    }

    public void showMovieFragment(int position) {
        FragmentManager fm = getFragmentManager();
        MovieFragment dialogFragment = new MovieFragment();
        Bundle args = new Bundle();
        Movie movie = movies.get(position);
        args.putInt("id", movie._id);
        args.putString("title", movie.title);
        args.putString("overview", movie.overview);
        args.putString("image", movie.poster);
        args.putString("date", movie.releaseDate);
        args.putString("url", movie.youtubeUrl);
        args.putInt("rating", movie.rating);
        dialogFragment.setArguments(args);
        dialogFragment.show(fm, "Sample Fragment");
    }

    public void  startMovieActivity(int position) {
        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
        Movie movie = movies.get(position);
        intent.putExtra("id", movie._id);
        intent.putExtra("title", movie.title);
        intent.putExtra("overview", movie.overview);
        intent.putExtra("image", movie.poster);
        intent.putExtra("date", movie.releaseDate);
        intent.putExtra("url", movie.youtubeUrl);
        intent.putExtra("rating", movie.rating);

        startActivity(intent);
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

                            //function to download and set youtube key
                            getVideoUrl(movie, id);

                            movies.add(movie);
                        }

                        for (i=0;i<movies.size();i++){
                            Log.v("result movie", ""+movies.get(i).title+"");
                        }

                        movieAdapter.addAll(movies);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getVideoUrl(final Movie movie, int id)  {

        DownloadYoutubeUrl task = new DownloadYoutubeUrl(movie);
        task.execute(new String[] { "https://api.themoviedb.org/3/movie/"+id+"/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed"});

    }

    private class DownloadYoutubeUrl extends AsyncTask<String, Void, String> {

        Movie movie;
        public DownloadYoutubeUrl(Movie mov) {
            this.movie = mov;
        }

        @Override
        protected String doInBackground(String... urls) {
            // we use the OkHttp library from https://github.com/square/okhttp
            OkHttpClient client = new OkHttpClient();
            okhttp3.Request request =
                    new okhttp3.Request.Builder()
                            .url(urls[0])
                            .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response.isSuccessful()) {
                try {
                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "Download failed";
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jArray = jsonObject.getJSONArray("results");

                String key = jArray.optJSONObject(0).getString("key");

                setVideoUrl(key);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public void setVideoUrl(String key) {
            movie.youtubeUrl = key;
        }
    }


}
