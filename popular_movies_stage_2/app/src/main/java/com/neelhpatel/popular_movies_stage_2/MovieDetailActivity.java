package com.neelhpatel.popular_movies_stage_2;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.neelhpatel.popular_movies_stage_2.adapters.ReviewInfoAdapter;
import com.neelhpatel.popular_movies_stage_2.adapters.TrailerInfoAdapter;
import com.neelhpatel.popular_movies_stage_2.databinding.ActivityMovieDetailBinding;
import com.neelhpatel.popular_movies_stage_2.model.AppDatabase;
import com.neelhpatel.popular_movies_stage_2.model.AppExecutors;
import com.neelhpatel.popular_movies_stage_2.model.MovieDetailViewModel;
import com.neelhpatel.popular_movies_stage_2.model.MovieDetailViewModelFactory;
import com.neelhpatel.popular_movies_stage_2.model.MovieInfo;
import com.neelhpatel.popular_movies_stage_2.model.ReviewInfo;
import com.neelhpatel.popular_movies_stage_2.model.TrailerInfo;
import com.neelhpatel.popular_movies_stage_2.utils.MovieDBJsonUtils;
import com.neelhpatel.popular_movies_stage_2.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {

    ActivityMovieDetailBinding mMovieDetailBinding;
    private static Button mFavoritesBtn;
    private static TextView mNoReviewsTv;
    private static TextView mNoTrailersTv;
    private static RecyclerView mReviewRecyclerView;
    private static RecyclerView mTrailerRecyclerView;
    private static TrailerInfoAdapter mTrailerInfoAdapter;
    private static ReviewInfoAdapter mReviewInfoAdapter;
    private static List<TrailerInfo> mTrailerInfos = new ArrayList<>();
    private static List<ReviewInfo> mReviewInfos = new ArrayList<>();

    private AppDatabase mDb;
    private boolean isFavorite;
    private MovieInfo movieInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        Intent intent = getIntent();
        if(intent.hasExtra(MainActivity.MOVIEINFO_KEY)) {
            movieInfo = intent.getParcelableExtra(MainActivity.MOVIEINFO_KEY);
            bindData(movieInfo);

            mDb = AppDatabase.getsInstance(getApplicationContext());

            MovieDetailViewModelFactory factory = new MovieDetailViewModelFactory(mDb, movieInfo.getId());
            final MovieDetailViewModel viewModel = ViewModelProviders.of(this, factory).get(MovieDetailViewModel.class);
            viewModel.getFavorite().observe(this, new Observer<MovieInfo>() {
                @Override
                public void onChanged(@Nullable MovieInfo currentInfo) {
                    setIsFavorite(currentInfo != null);
                    setButtonText();
                    viewModel.getFavorite().removeObserver(this);
                }
            });


            mFavoritesBtn = mMovieDetailBinding.addFavoritesBtn;
            mNoReviewsTv = mMovieDetailBinding.emptyReviewTv;
            mNoTrailersTv = mMovieDetailBinding.emptyTrailerTv;

            mTrailerRecyclerView = findViewById(R.id.movie_trailer_rv);
            mTrailerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            fetchTrailers(movieInfo.getId());
            mTrailerInfoAdapter = new TrailerInfoAdapter(mTrailerInfos, this);
            mTrailerRecyclerView.setAdapter(mTrailerInfoAdapter);

            mReviewRecyclerView = findViewById(R.id.movie_reviews_rv);
            mReviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            fetchReviews(movieInfo.getId());
            mReviewInfoAdapter = new ReviewInfoAdapter(mReviewInfos, this);
            mReviewRecyclerView.setAdapter(mReviewInfoAdapter);
        } else {
            finish();
        }
    }

    public void setIsFavorite(boolean state) {
        isFavorite = state;
    }

    public void setButtonText() {
        if (isFavorite) {
            mFavoritesBtn.setText(getResources().getString(R.string.remove_favorites_tv));
        } else {
            mFavoritesBtn.setText(getResources().getString(R.string.add_favorites_tv));
        }
    }

    private void bindData(MovieInfo movieInfo) {
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

    public void modifyFavorites(View view) {
        setIsFavorite(!isFavorite);
        setButtonText();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (isFavorite) {
                    mDb.favoritesDao().insertFavorite(movieInfo);
                } else {
                    mDb.favoritesDao().deleteFavorite(movieInfo);
                }
            }
        });
    }

    /**
     * Starts AsyncTask which will fetch movie review data in the background
     */
    private void fetchReviews(int movieId) {
        if(NetworkUtils.isConnectedToInternet(this)) {
            URL reviewsUrl = NetworkUtils.getMovieReviews(movieId);
            new MovieDetailActivity.MovieReviewTask().execute(reviewsUrl);
        } else {
            Toast.makeText(this, getResources().getString(R.string.toast_internet_string),
                Toast.LENGTH_SHORT).show();
        }
    }

    static class MovieReviewTask extends AsyncTask<URL, Void, List<ReviewInfo>> {
        @Override
        protected List<ReviewInfo> doInBackground(URL... urls) {
            if(android.os.Debug.isDebuggerConnected())
                android.os.Debug.waitForDebugger();

            URL url = urls[0];
            List<ReviewInfo> reviewInfos = new ArrayList<>();

            try {
                String jsonMainResponse = NetworkUtils.getResponseFromHttpUrl(url);
                reviewInfos = MovieDBJsonUtils.getReviewsFromJson(jsonMainResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return reviewInfos;
        }

        @Override
        protected void onPostExecute(List<ReviewInfo> reviewInfos) {
            mReviewInfos = reviewInfos;
            mReviewInfoAdapter.changeData(reviewInfos);
            if (mReviewInfos.isEmpty()) {
                mReviewRecyclerView.setVisibility(View.GONE);
                mNoReviewsTv.setVisibility(View.VISIBLE);
            }
            else {
                mReviewRecyclerView.setVisibility(View.VISIBLE);
                mNoReviewsTv.setVisibility(View.GONE);
            }
            super.onPostExecute(reviewInfos);
        }
    }

    /**
     * Starts AsyncTask which will fetch movie trailer data in the background
     */
    private void fetchTrailers(int movieId) {
        if(NetworkUtils.isConnectedToInternet(this)) {
            URL trailersUrl = NetworkUtils.getMovieTrailers(movieId);
            new MovieDetailActivity.MovieTrailerTask().execute(trailersUrl);
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
            if (mTrailerInfos.isEmpty()) {
                mTrailerRecyclerView.setVisibility(View.GONE);
                mNoTrailersTv.setVisibility(View.VISIBLE);
            }
            else {
                mTrailerRecyclerView.setVisibility(View.VISIBLE);
                mNoTrailersTv.setVisibility(View.GONE);
            }
            super.onPostExecute(trailerInfos);
        }
    }
}
