package br.felipepedroso.udacitymovieapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        MovieInfo movieInfo = (MovieInfo)getIntent().getExtras().getParcelable(DetailsFragment.MOVIE_INFO_KEY);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, DetailsFragment.newInstance(movieInfo))
                .commit();
    }
}
