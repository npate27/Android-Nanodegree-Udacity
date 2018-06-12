package com.neelhpatel.popular_movies_stage_2;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.neelhpatel.popular_movies_stage_2.adapters.TrailerInfoAdapter;
import com.neelhpatel.popular_movies_stage_2.databinding.ActivityMovieDetailBinding;
import com.neelhpatel.popular_movies_stage_2.model.MovieInfo;
import com.neelhpatel.popular_movies_stage_2.model.TrailerInfo;
import com.neelhpatel.popular_movies_stage_2.utils.MovieDBJsonUtils;
import com.neelhpatel.popular_movies_stage_2.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity implements TrailerInfoAdapter.TrailersOnClickHandler {

    ActivityMovieDetailBinding mMovieDetailBinding;
    private static TrailerInfoAdapter mTrailerInfoAdapter;
    private static List<TrailerInfo> mTrailerInfos = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        Intent intent = getIntent();
        if(intent.hasExtra(MainActivity.MOVIEINFO_KEY)) {
            MovieInfo movieInfo = intent.getParcelableExtra(MainActivity.MOVIEINFO_KEY);
            bindData(movieInfo);
            RecyclerView recyclerView = findViewById(R.id.movie_trailer_rv);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            fetchTrailers(movieInfo.getId());
            mTrailerInfoAdapter = new TrailerInfoAdapter(this, mTrailerInfos, this);
            recyclerView.setAdapter(mTrailerInfoAdapter);
        } else {
            finish();
        }
    }

    private void bindData(MovieInfo movieInfo){
        URL posterImageUrl = NetworkUtils.getMoviePosterUrl(movieInfo.getPosterPath());
        Glide.with(this)
            .load(new GlideUrl(posterImageUrl))
            .apply(new RequestOptions()
                .placeholder(R.drawable.loading_circle)
                .error(R.drawable.stock_image_not_available))
            .into(mMovieDetailBinding.posterImageIv);
        mMovieDetailBinding.posterImageIv.setContentDescription(movieInfo.getTitle());


        String userRatingString = getString(R.string.format_rating, movieInfo.getVoteAverage());
        String releaseDateString = getString(R.string.format_release_date, movieInfo.getReleaseDateFormatted());
        mMovieDetailBinding.userRatingTv.setText(userRatingString);
        mMovieDetailBinding.releaseDateTv.setText(releaseDateString);
        mMovieDetailBinding.plotOverviewTv.setText(movieInfo.getOverview());
        setTitle(movieInfo.getTitle());
    }

    @Override
    public void onClick(TrailerInfo trailerInfo) {
        //TODO: Start Intent to watch video
        //TODO: Share video capability?
    }

    /**
     * Starts AsyncTask which will fetch movie trailer data in the background
     */
    private void fetchTrailers(int movieId) {
        if(NetworkUtils.isConnectedToInternet(this)) {
            URL mainMovieUrl = NetworkUtils.getMovieTrailers(movieId);
            new MovieDetailActivity.MovieTrailerTask().execute(mainMovieUrl);
        } else {
            Toast.makeText(this, getResources().getString(R.string.toast_internet_string),
                Toast.LENGTH_SHORT).show();
        }
    }

    static class MovieTrailerTask extends AsyncTask<URL, Void, List<TrailerInfo>> {
        @Override
        protected List<TrailerInfo> doInBackground(URL... urls) {
            if(android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();

            URL url = urls[0];
            List<TrailerInfo> trailerInfos = new ArrayList<>();

            try {
                String jsonMainResponse = NetworkUtils.getResponseFromHttpUrl(url);
                trailerInfos = MovieDBJsonUtils.getTrailersFromJson(jsonMainResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return trailerInfos;
        }

        @Override
        protected void onPostExecute(List<TrailerInfo> trailerInfos) {
            mTrailerInfos = trailerInfos;
            mTrailerInfoAdapter.changeData(mTrailerInfos);
            super.onPostExecute(trailerInfos);
        }
    }
}
