package com.kamberov.popularmovies.utilities;

/**
 * Created by Rustam Kamberov on 04/02/2017.
 */

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Theis class is to be communicate with the TheMovieDb server
 */
public class NetworkUtils {

    // https://api.themoviedb.org/3/movie/550?api_key=2df1665618be411b06a473ed467c3ca5
    // https://api.themoviedb.org/3/movie/popular?api_key=2df1665618be411b06a473ed467c3ca5
    // https://api.themoviedb.org/3/movie/top_rated?api_key=2df1665618be411b06a473ed467c3ca5

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String THEMOVIEDB_URL = "https://api.themoviedb.org/3/movie/";
    //"http://api.themoviedb.org/3/search/movie?query=Monsters+University&api_key=MYAPIKEY");
    // http://api.themoviedb.org/3/search/movie?id=328111&api_key=2df1665618be411b06a473ed467c3ca5
    // http://api.themoviedb.org/3/movie/328111/images?api_key=2df1665618be411b06a473ed467c3ca5

    private static final String PARAM_API = "api_key";
    private static final String API_KEY = "2df1665618be411b06a473ed467c3ca5";

    private static final String THEMOVIEDB_URL_IMAGE = "http://image.tmdb.org/t/p/";
    private static final String SIZE_TYPE = "w185";

    /**
     * This method returns the result for one page
     *
     * @param orderType The kind of order that will be retrieved by.
     * @return The URL to use to query the MovieDB server.
     */
    public static URL buildUrl(String orderType) {
        Uri uri = Uri.parse(THEMOVIEDB_URL + orderType).buildUpon()
                .appendQueryParameter(PARAM_API, API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static URL buildImageUrl(String sizeType, String imageRelativeUrl){
        Uri imageUri = Uri.parse(THEMOVIEDB_URL_IMAGE + sizeType + imageRelativeUrl).buildUpon()
                .build();

        URL imageUrl = null;
        try {
            imageUrl = new URL(imageUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return imageUrl;
    }

    /**
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream inputStream = httpURLConnection.getInputStream();

            Scanner scanner = new Scanner(inputStream);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            httpURLConnection.disconnect();
        }
    }

    //public static boolean isOnline
}
