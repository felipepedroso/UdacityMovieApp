package br.felipepedroso.udacitymovieapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetailsFragment extends Fragment {
    public static final String MOVIE_INFO_KEY = "movie";
    private MovieInfo movieInfo;

    public DetailsFragment() {
        // Required empty public constructor
    }

    public static DetailsFragment newInstance(MovieInfo movieInfo) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(MOVIE_INFO_KEY, movieInfo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieInfo = (MovieInfo) getArguments().getParcelable(MOVIE_INFO_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        ImageView moviePoster = (ImageView) view.findViewById(R.id.moviePoster);
        Picasso.with(getContext()).load(getString(R.string.tmdb_image_base_url) + movieInfo.getPosterPath()).into(moviePoster);

        getActivity().setTitle(movieInfo.getTitle());

        return view;
    }
}
