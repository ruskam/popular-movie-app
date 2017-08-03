package com.kamberov.popularmovies.utilities;

/**
 * Created by ESR14 on 14/02/2017.
 */

public final class MovieReleaseUtils {

    public static String getYear(String releaseDate){
        String releaseYear = releaseDate.split("-")[0];
        return releaseYear;
    }

}
