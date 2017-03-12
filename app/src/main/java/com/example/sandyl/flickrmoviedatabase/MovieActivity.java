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
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Flickr");

        setContentView(R.layout.movie_activity);
        TextView movieTextView = (TextView) findViewById(R.id.movieTitle);
        TextView overviewTextView = (TextView) findViewById(R.id.movieOverview);
        TextView releaseTextView = (TextView) findViewById(R.id.movieReleaseDate);
        btn = (Button) findViewById(R.id.playBtn);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        title = intent.getStringExtra("title");
        overview = intent.getStringExtra("overview");
        image = intent.getStringExtra("image");
        release = intent.getStringExtra("release");
        youtubeUrl = intent.getStringExtra("url");
        movieTextView.setText(title);
        overviewTextView.setText(overview);
        releaseTextView.setText(release);
        getImagePicasso("http://image.tmdb.org/t/p/w185/"+image);

        addListenerOnRatingBar();

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
        // txtRatingValue = (TextView) findViewById(R.id.txtRatingValue);

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
