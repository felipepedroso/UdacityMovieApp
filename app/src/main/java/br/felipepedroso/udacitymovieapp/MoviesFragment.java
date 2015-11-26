package br.felipepedroso.udacitymovieapp;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoviesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoviesFragment extends Fragment {
    private MoviesAdapter moviesAdapter;

    public MoviesFragment() {
    }

    public static MoviesFragment newInstance() {//(String param1, String param2) {
        MoviesFragment fragment = new MoviesFragment();
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    private void updateMovies() {
        FetchMoviesTask moviesTask = new FetchMoviesTask();
        moviesTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.gridview_movies);
        moviesAdapter = new MoviesAdapter(getContext());
        gridView.setAdapter(moviesAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieInfo movieInfo = (MovieInfo) parent.getItemAtPosition(position);

                Intent intent = new Intent(MoviesFragment.this.getActivity(),DetailsActivity.class);
                intent.putExtra(DetailsFragment.MOVIE_INFO_KEY, movieInfo);

                startActivity(intent);
            }
        });

        return view;
    }

    private class FetchMoviesTask extends AsyncTask<String, Void, MovieInfo[]> {

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        @Override
        protected MovieInfo[] doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String moviesJsonStr = null;
            MovieInfo[] parsedResults = null;

            try {
                final String UPCOMING_MOVIES_BASE_URL = "http://api.themoviedb.org/3/movie/upcoming?";

                Uri builtUri = Uri.parse(UPCOMING_MOVIES_BASE_URL).buildUpon()
                        .appendQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API_KEY)
                        .build();


                URL url = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                moviesJsonStr = buffer.toString();

                Log.d(LOG_TAG, moviesJsonStr);
                parsedResults = getMovieDataFromJson(moviesJsonStr);
                Log.d(LOG_TAG, Arrays.toString(parsedResults));
            } catch (MalformedURLException e) {
                Log.e(LOG_TAG, "Error ", e);
            } catch (ProtocolException e) {
                Log.e(LOG_TAG, "Error ", e);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Error ", e);
            }

            return parsedResults;
        }

        public MovieInfo[] getMovieDataFromJson(String moviesJsonString) throws JSONException {
            final String TMDB_RESULTS = "results";
            final String TMDB_ORIGINAL_TITLE = "original_title";
            final String TMDB_ID = "id";
            final String TMDB_POSTER_PATH = "poster_path";
            final String TMDB_POPULARITY = "popularity";
            final String TMDB_RELEASE_DATE = "release_date";
            final String TMDB_VOTE_AVERAGE = "vote_average";
            final String TMDB_OVERVIEW = "overview";

            JSONObject moviesJson = new JSONObject(moviesJsonString);
            JSONArray moviesArray = moviesJson.getJSONArray(TMDB_RESULTS);

            MovieInfo[] movieInfos = new MovieInfo[moviesArray.length()];

            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject movieObj = moviesArray.getJSONObject(i);
                String id = movieObj.getString(TMDB_ID);
                String title = movieObj.getString(TMDB_ORIGINAL_TITLE);
                String synopsis = movieObj.getString(TMDB_OVERVIEW);
                String posterPath = movieObj.getString(TMDB_POSTER_PATH);
                double popularity = movieObj.getDouble(TMDB_POPULARITY);
                double userRating = movieObj.getDouble(TMDB_VOTE_AVERAGE);
                String releaseDate = movieObj.getString(TMDB_RELEASE_DATE);

                movieInfos[i] = new MovieInfo(id, title, synopsis, posterPath, popularity, userRating, releaseDate);
            }

            return movieInfos;
        }

        @Override
        protected void onPostExecute(MovieInfo[] movieInfos) {
            if (moviesAdapter != null){
                moviesAdapter.clear();

                for (MovieInfo movieInfo : movieInfos){
                    moviesAdapter.add(movieInfo);
                }
            }
        }
    }

}
