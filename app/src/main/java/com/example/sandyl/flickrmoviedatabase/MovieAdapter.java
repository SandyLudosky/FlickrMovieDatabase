package com.example.sandyl.flickrmoviedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by sandyl on 2017-03-11.
 */


public class MovieAdapter extends ArrayAdapter<Movie> {


    Movie movieT;
    Context context;
    View view;


    public  MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
        this.context = context;
    }


    private static class ViewHolder {
        TextView titleTextView;
        TextView overViewTextView;
        ImageView imageView;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Movie movie = getItem(position);
        movieT = movie;
        view = convertView;
        ViewHolder viewHolder = new ViewHolder();

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie_layout, null);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.movieTitle);
            viewHolder.overViewTextView = (TextView) convertView.findViewById(R.id.movieOverview);

            viewHolder.titleTextView.setText(movie.title);
            viewHolder.overViewTextView.setText(movie.overview);
            convertView.setTag(viewHolder);

            //Picasso
            getImagePicasso("http://image.tmdb.org/t/p/w185/"+movie.poster, convertView);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private void getImagePicasso(String uri, View v) {

        ImageView ivBasicImage = (ImageView) v.findViewById(R.id.movieImage);
        Picasso.with(context).load(uri).into(ivBasicImage);

        Picasso.with(context).load(uri).fit().centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(ivBasicImage);
    }

}