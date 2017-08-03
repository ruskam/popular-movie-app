package com.kamberov.popularmovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.kamberov.popularmovies.model.Movie;
import com.kamberov.popularmovies.utilities.MovieReleaseUtils;
import com.kamberov.popularmovies.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

public class MovieActivity extends AppCompatActivity {

    private Movie movie;
    private TextView mTitle;
    private TextView mRatingText;
    private TextView mRating;
    private ImageView mPosterImage;
    private TextView mOverviewTitle;
    private TextView mOverview;

    private static final String DEFAULT_IMAGE_SIZE = "w185";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        mTitle = (TextView) findViewById(R.id.tv_movie_title);
        mRatingText = (TextView) findViewById(R.id.tv_movie_rating_text);
        mRatingText.setText("Rating: ");
        mRating = (TextView) findViewById(R.id.tv_movie_rating);
        mPosterImage = (ImageView) findViewById(R.id.iv_poster_image_individual);
        mOverviewTitle = (TextView) findViewById(R.id.tv_movie_overview_title);
        mOverviewTitle.setText("About the movie");
        mOverview = (TextView) findViewById(R.id.tv_movie_overview);


        Intent intentReceived = getIntent();

        if (intentReceived != null) {
            if (intentReceived.hasExtra("movieSelected")) {
                movie = (Movie) intentReceived.getSerializableExtra("movieSelected");

                fillViewsWithData(movie);

                loadPoster();
            }
        }
    }

    private void loadPoster() {
        String imageSize = DEFAULT_IMAGE_SIZE;

        new FetchImageTask().execute(imageSize);
    }

    private void fillViewsWithData(Movie movie){
        mTitle.setText(movie.getTitle() + " ("+ MovieReleaseUtils.getYear(movie.getDate()) + ")");
        mRating.setText(String.valueOf(movie.getRating()));
        mOverview.setText(movie.getOverview());
    }

    private class FetchImageTask extends AsyncTask<String, Void, URL> {

        @Override
        protected URL doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String imageSize = params[0];

            URL imageUrl = NetworkUtils.buildImageUrl(imageSize, movie.getImage_thumbnail());
            try {
                imageUrl = new URL(imageUrl.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return imageUrl;
        }

        @Override
        protected void onPostExecute(URL imageUrl) {

            if (imageUrl != null) {
                Picasso.with(mPosterImage.getContext()).load(imageUrl.toString()).into(mPosterImage);
            } else {
                Log.i("URL debug", "Issues with URL");
            }
        }
    }

    private void setImageSize(String imageSize){

    }
}
