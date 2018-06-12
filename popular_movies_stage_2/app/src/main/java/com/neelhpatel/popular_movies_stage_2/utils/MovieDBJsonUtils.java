package com.neelhpatel.popular_movies_stage_2.utils;

import com.neelhpatel.popular_movies_stage_2.model.MovieInfo;
import com.neelhpatel.popular_movies_stage_2.model.ReviewInfo;
import com.neelhpatel.popular_movies_stage_2.model.TrailerInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public final class MovieDBJsonUtils {
    private static final String STATUS_CODE = "status_code";

    private static final String RESULTS = "results";
    private static final String ID = "id";
    private static final String POSTER_PATH = "poster_path";

    private static final String TITLE = "original_title";
    private static final String RELEASE_DATE = "release_date";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String OVERVIEW = "overview";

    private static final String VIDEO_TYPE = "type";
    private static final String TYPE_TRAILER = "Trailer";
    private static final String TRAILER_NAME = "name";
    private static final String TRAILER_KEY = "key";
    private static final String TRAILER_SITE = "site";
    private static final String SITE_YOUTUBE = "YouTube";

    private static final String REVIEW_AUTHOR = "author";
    private static final String REVIEW_CONTENT = "content";
    private static final String REVIEW_URL = "url";

    public static List<MovieInfo> getMoviesFromJson(String moviesJsonStr)
            throws JSONException {

        JSONObject moviesJson = new JSONObject(moviesJsonStr);

        if (moviesJson.has(STATUS_CODE)) {
            return null;
        }

        JSONArray jsonMoviesArray = moviesJson.getJSONArray(RESULTS);
        List<MovieInfo> movieInfos = new ArrayList<>();

        for (int i = 0; i < jsonMoviesArray.length(); i++) {
            int id;
            String posterPath;
            String title;
            String releaseDate;
            double voteAverage;
            String overview;

            JSONObject movie = jsonMoviesArray.getJSONObject(i);

            id = movie.getInt(ID);
            posterPath = movie.optString(POSTER_PATH);
            title = movie.optString(TITLE);
            releaseDate = movie.optString(RELEASE_DATE);
            voteAverage = movie.getDouble(VOTE_AVERAGE);
            overview = movie.optString(OVERVIEW);

            MovieInfo movieInfo = new MovieInfo(id, posterPath, title, releaseDate,
                voteAverage, overview);
            movieInfos.add(movieInfo);
        }
        return movieInfos;
    }

    public static List<TrailerInfo> getTrailersFromJson(String movieVideosJsonStr)
        throws JSONException {

        JSONObject movieVideosJson = new JSONObject(movieVideosJsonStr);

        if (movieVideosJson.has(STATUS_CODE)) {
            return null;
        }

        JSONArray jsonMovieVideosArray = movieVideosJson.getJSONArray(RESULTS);
        List<TrailerInfo> trailerInfos = new ArrayList<>();

        for (int i = 0; i < jsonMovieVideosArray.length(); i++) {
            String key;
            String name;

            JSONObject trailer = jsonMovieVideosArray.getJSONObject(i);
            if(trailer.optString(VIDEO_TYPE).equals(TYPE_TRAILER)
                && trailer.optString(TRAILER_SITE).equals(SITE_YOUTUBE)) {
                key = trailer.optString(TRAILER_KEY);
                name = trailer.optString(TRAILER_NAME);

                TrailerInfo trailerInfo = new TrailerInfo(key, name);
                trailerInfos.add(trailerInfo);
            }
        }
        return trailerInfos;
    }

    public static List<ReviewInfo> getReviewsFromJson(String movieReviewsJsonStr)
        throws JSONException {

        JSONObject movieReviewsJson = new JSONObject(movieReviewsJsonStr);

        if (movieReviewsJson.has(STATUS_CODE)) {
            return null;
        }

        JSONArray jsonMovieReviewsArray = movieReviewsJson.getJSONArray(RESULTS);
        List<ReviewInfo> reviewInfos = new ArrayList<>();

        for (int i = 0; i < jsonMovieReviewsArray.length(); i++) {
            String author;
            String textContent;
            String url;

            JSONObject review = jsonMovieReviewsArray.getJSONObject(i);
            author = review.optString(REVIEW_AUTHOR);
            textContent = review.optString(REVIEW_CONTENT);
            url = review.optString(REVIEW_URL);

            ReviewInfo reviewInfo = new ReviewInfo(author, textContent, url);
            reviewInfos.add(reviewInfo);
        }
        return reviewInfos;
    }
}


