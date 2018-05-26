package com.neelhpatel.popular_movies_stage_1.utils;

import com.neelhpatel.popular_movies_stage_1.model.MovieInfo;

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
}


