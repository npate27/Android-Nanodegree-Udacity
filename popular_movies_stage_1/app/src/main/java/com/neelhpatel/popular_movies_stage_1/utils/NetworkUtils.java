package com.neelhpatel.popular_movies_stage_1.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String MOVIES_BASE_URL =
        "https://api.themoviedb.org/3/movie";
    private static final String MOVIES_POPULAR =
        "popular";
    private static final String MOVIES_TOP_RATED =
        "top_rated";
    private static final String API_KEY_STRING =
        "api_key";

    //PUT KEY HERE
    private static final String API_KEY =
        "";
    
    private static final String POSTER_BASE_URL =
        "http://image.tmdb.org/t/p";
    private static final String POSTER_SIZE =
        "w185";

    /**
     * Generates URL that will return a JSONArray with all main data required for basic layout
     * of MainActivity and  MovieDetailActivity
     *
     * @param isPopularSelected current sort preference
     * @return URL which will return JSONArray of sorted movies
     */
    public static URL getMainUrl (boolean isPopularSelected) {
        Uri mainMoviesQueryUri = Uri.parse(MOVIES_BASE_URL);
        if (isPopularSelected) {
            mainMoviesQueryUri = mainMoviesQueryUri.buildUpon()
                .appendPath(MOVIES_POPULAR)
                .appendQueryParameter(API_KEY_STRING, API_KEY)
                .build();
        } else {
            mainMoviesQueryUri = mainMoviesQueryUri.buildUpon()
                .appendPath( MOVIES_TOP_RATED)
                .appendQueryParameter(API_KEY_STRING, API_KEY)
                .build();
        }
        return generateUrl(mainMoviesQueryUri);
    }

    /**
     * Generates URL that will return more specific JSON pertaining to a specific movie
     * via it's movie id field
     *
     * @param movieId movie to be queried further
     * @return URL which will return more specific details of movie
     */
    public static URL getMovieDetailUrl(String movieId) {
        Uri movieDetailUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                .appendPath(movieId)
                .appendQueryParameter(API_KEY_STRING, API_KEY)
                .build();
        return generateUrl(movieDetailUri);
    }


    /**
     * Generates URL that will show the corresponding movie's poster at the specified
     * poster size defined above.
     *
     * @param imagePath path of poster image
     * @return URL which will return poster of movie at given size
     */
    public static URL getMoviePosterUrl(String imagePath) {
        Uri moviePosterUri = Uri.parse(POSTER_BASE_URL).buildUpon()
            .appendPath(POSTER_SIZE)
            .appendPath(imagePath.substring(1))
            .build();
        return generateUrl(moviePosterUri);

    }


    private static URL generateUrl(Uri uri){
        try {
            return new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            String response = null;
            if (hasInput) {
                response = scanner.next();
            }
            scanner.close();
            return response;
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * Taken from Android Developer Guides.
     *
     * @param context app context
     * @return true if there's internet, otherwise false
     */
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}

