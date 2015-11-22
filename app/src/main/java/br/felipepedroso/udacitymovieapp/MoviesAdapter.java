package br.felipepedroso.udacitymovieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

/**
 * Created by FelipeAugusto on 22/11/2015.
 */
public class MoviesAdapter extends ArrayAdapter<MovieInfo> {
    private String TMDB_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w500";

    public MoviesAdapter(Context context) {
        super(context, R.layout.grid_item_movie);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_movie,parent, false);
        }

        MovieInfo movieInfo = getItem(position);

        ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);

        TMDBImageView imageView = (TMDBImageView) convertView.findViewById(R.id.moviePoster);
        imageView.setProgressBar(progressBar);
        imageView.setImageUrl(TMDB_IMAGE_BASE_URL + movieInfo.getPosterPath());

        return convertView;
    }
}
