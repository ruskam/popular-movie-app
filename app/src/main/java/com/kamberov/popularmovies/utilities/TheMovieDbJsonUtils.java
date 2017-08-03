package com.kamberov.popularmovies.utilities;

import android.content.Context;
import android.util.Log;

import com.kamberov.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rustam Kamberov on 04/02/2017.
 */

public class TheMovieDbJsonUtils {

    public static Movie [] getMovieDbStringFromJson(Context context, String jsonMovieDbResponse) throws JSONException{

        final String MOVIE_LIST = "results";

        final String MOVIE_ID = "id";
        final String MOVIE_TITLE = "original_title";
        final String MOVIE_IMAGE_THUMBNAIL = "poster_path";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_RATING = "vote_average";
        final String MOVIE_RELEASE_DATE = "release_date";


        Movie[] parsedMovieData = null;

        JSONObject moviesJson = new JSONObject(jsonMovieDbResponse);

        JSONArray moviesArray = moviesJson.getJSONArray(MOVIE_LIST);

        parsedMovieData = new Movie[moviesArray.length()];

        for (int i = 0; i < moviesArray.length(); i++) {
            /*
            String title;
            String image_thumbnail;
            String overview;
            double rating;
            String date;
            */
            /* Get the JSON object representing a movie */
            JSONObject movieJsonObject = moviesArray.getJSONObject(i);

            Movie movie = new Movie();
            movie.setId(movieJsonObject.getInt(MOVIE_ID));
            movie.setTitle(movieJsonObject.getString(MOVIE_TITLE));
            movie.setImage_thumbnail(movieJsonObject.getString(MOVIE_IMAGE_THUMBNAIL));
            movie.setOverview(movieJsonObject.getString(MOVIE_OVERVIEW));
            movie.setRating(movieJsonObject.getDouble(MOVIE_RATING));
            movie.setDate(movieJsonObject.getString(MOVIE_RELEASE_DATE));

            Log.i("movie", movie.toString());
            parsedMovieData[i] = movie;
        }

        return parsedMovieData;
    }
}
