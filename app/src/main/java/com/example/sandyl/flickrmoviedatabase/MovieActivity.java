package com.example.sandyl.flickrmoviedatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieActivity extends AppCompatActivity {

    int id;
    String title;
    String overview;
    String image;
    String release;
    String youtubeUrl;
    RatingBar ratingBar;
    int rating;

    Button btn;

    TextView movieTextView;
    TextView voteTextView;
    TextView overviewTextView;
    TextView releaseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Flickr");

        setContentView(R.layout.movie_activity);

        movieTextView = (TextView) findViewById(R.id.movieTitle);
        overviewTextView = (TextView) findViewById(R.id.movieOverview);
        releaseTextView = (TextView) findViewById(R.id.movieReleaseDate);
        voteTextView = (TextView) findViewById(R.id.voteTextView);

        btn = (Button) findViewById(R.id.playBtn);

        addListenerOnRatingBar();

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(MovieActivity.this, VideoActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("youtube", youtubeUrl);
                startActivity(intent);

            }
        });

    }

    public void getActivityIntent() {

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        title = intent.getStringExtra("title");
        overview = intent.getStringExtra("overview");
        image = intent.getStringExtra("image");
        release = intent.getStringExtra("release");
        youtubeUrl = intent.getStringExtra("url");
        rating = intent.getIntExtra("rating", -1);
        movieTextView.setText(title);
        overviewTextView.setText(overview);
        releaseTextView.setText(release);

        getImagePicasso("http://image.tmdb.org/t/p/w185/"+image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_dismiss:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getImagePicasso(String uri) {

        ImageView ivBasicImage = (ImageView) findViewById(R.id.movieImage);


        Picasso.with(this).load(uri).fit().centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(ivBasicImage);

    }

    public void addListenerOnRatingBar() {

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setNumStars(10);
        ratingBar.setRating(rating);
        voteTextView.setText(""+rating+"/10");

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                //  txtRatingValue.setText(String.valueOf(rating));

            }
        });
    }
}
