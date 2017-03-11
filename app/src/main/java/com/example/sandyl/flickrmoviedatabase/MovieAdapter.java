package com.example.sandyl.flickrmoviedatabase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sandyl on 2017-03-11.
 */


public class MovieAdapter extends ArrayAdapter<Movie> {


    Movie movieT;


    public  MovieAdapter(Context context, ArrayList<Movie> movies) {
        super(context, 0, movies);
    }


    private static class ViewHolder {
        TextView titleTextView;
        TextView overViewTextView;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Movie movie = getItem(position);
        movieT = movie;
        ViewHolder viewHolder = new ViewHolder();

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_movie_layout, null);
            viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.movieTitle);
            viewHolder.overViewTextView = (TextView) convertView.findViewById(R.id.movieOverview);

            viewHolder.titleTextView.setText(movie.title);
            viewHolder.overViewTextView.setText(movie.overview);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

}