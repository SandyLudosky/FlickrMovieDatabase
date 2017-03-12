package com.example.sandyl.flickrmoviedatabase;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by sandyl on 2017-03-12.
 */

public class MovieFragment extends DialogFragment {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_activity, container, false);

        movieTextView = (TextView) rootView.findViewById(R.id.movieTitle);
        overviewTextView = (TextView) rootView.findViewById(R.id.movieOverview);
        releaseTextView = (TextView) rootView.findViewById(R.id.movieReleaseDate);
        voteTextView = (TextView) rootView.findViewById(R.id.voteTextView);

        getFragmentArgs(rootView);
        addListenerOnRatingBar(rootView);

        btn = (Button) rootView.findViewById(R.id.playBtn);
        addButtonListener();

        return rootView;
    }


    public void getFragmentArgs(View v) {

        id = getArguments().getInt("id");
        title =  getArguments().getString("title");
        overview =  getArguments().getString("overview");
        image =  getArguments().getString("image");
        release =  getArguments().getString("release");
        youtubeUrl =  getArguments().getString("url");
        rating =  getArguments().getInt("rating");
        movieTextView.setText(title);
        overviewTextView.setText(overview);
        releaseTextView.setText(release);

        getDialog().setTitle(""+title+"");

        getImagePicasso("http://image.tmdb.org/t/p/w185/"+image, v);
    }

    private void getImagePicasso(String uri, View v) {

        ImageView ivBasicImage = (ImageView) v.findViewById(R.id.movieImage);


        Picasso.with(v.getContext()).load(uri).fit().centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(ivBasicImage);

    }

    public void addListenerOnRatingBar(View v) {

        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
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

    public void addButtonListener() {
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), VideoActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("youtube", youtubeUrl);
                startActivity(intent);

            }
        });
    }

}
