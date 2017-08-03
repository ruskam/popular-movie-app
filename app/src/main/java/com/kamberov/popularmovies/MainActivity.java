package com.kamberov.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kamberov.popularmovies.MovieAdapter.MovieAdapterOnClickHandler;
import com.kamberov.popularmovies.model.Movie;
import com.kamberov.popularmovies.utilities.NetworkUtils;
import com.kamberov.popularmovies.utilities.TheMovieDbJsonUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapterOnClickHandler {

    public static final String MOVIE_TO_SEND = "movieSelected";

    private static final String ORDER_POPULAR = "popular";

    private static final String ORDER_TOP_RATED = "top_rated";

    private static final String DEFAULT_ORDER = ORDER_POPULAR;

    private TextView mErrorMessage;

    private RecyclerView mRecyclerView;

    private ProgressBar mLoadingIndicator;

    private MovieAdapter mMovieAdapter;

    private GridLayoutManager mGridLayoutManager;

    /**
     * Here will be MovieAdapter
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        mErrorMessage = (TextView) findViewById(R.id.tv_error_message);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        mGridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mMovieAdapter = new MovieAdapter(this);
        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData(DEFAULT_ORDER);
    }

    private void loadMovieData(String orderType) {
        showMovieDataView();

        new FetchMoviesTask().execute(orderType);
    }

    private void showMovieDataView() {
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(RecyclerView.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = MovieActivity.class;
        Intent intentToStartTheActivity = new Intent(context, destinationClass);
        intentToStartTheActivity.putExtra(MOVIE_TO_SEND, movie);
        startActivity(intentToStartTheActivity);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, Movie[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String orderType = params[0];

            URL movieRequestUrl = NetworkUtils.buildUrl(orderType);
            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                Movie[] movieJsonData = TheMovieDbJsonUtils
                        .getMovieDbStringFromJson(MainActivity.this, jsonMovieResponse);

                return movieJsonData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        protected void onPostExecute(Movie[] movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                showMovieDataView();
                mMovieAdapter.setWeatherData(movieData);
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movie_menu, menu);

        MenuItem selectedByDefault = menu.findItem(R.id.sort_by_most_popular);
        selectedByDefault.setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort_by_most_popular: {
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }
                loadMovieData(ORDER_POPULAR);
                return true;
            }

            case R.id.sort_by_top_rated: {
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                }

                loadMovieData(ORDER_TOP_RATED);
                return true;
            }

            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
}























