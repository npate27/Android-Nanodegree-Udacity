package com.neelhpatel.popular_movies_stage_1;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.neelhpatel.popular_movies_stage_1.databinding.ActivityMovieDetailBinding;
import com.neelhpatel.popular_movies_stage_1.model.MovieInfo;
import com.neelhpatel.popular_movies_stage_1.utils.NetworkUtils;

import java.net.URL;

public class MovieDetailActivity extends AppCompatActivity {

    ActivityMovieDetailBinding mMovieDetailBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        Intent intent = getIntent();
        if(intent.hasExtra(MainActivity.MOVIEINFO_KEY)) {
            MovieInfo movieInfo = intent.getParcelableExtra(MainActivity.MOVIEINFO_KEY);
            bindData(movieInfo);
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
}
