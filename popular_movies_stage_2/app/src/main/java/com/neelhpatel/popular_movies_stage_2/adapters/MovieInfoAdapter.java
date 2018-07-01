package com.neelhpatel.popular_movies_stage_2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.neelhpatel.popular_movies_stage_2.model.MovieInfo;
import com.neelhpatel.popular_movies_stage_2.R;
import com.neelhpatel.popular_movies_stage_2.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieInfoAdapter extends RecyclerView.Adapter<MovieInfoAdapter.ViewHolder> {
    private final Context mContext;
    private List<MovieInfo> mMovieInfos;
    private final MoviesOnClickHandler mClickHandler;

    public MovieInfoAdapter(Context context, List<MovieInfo> movieInfos, MoviesOnClickHandler clickHandler) {
        mContext = context;
        mMovieInfos = movieInfos;
        mClickHandler = clickHandler;
    }

    public interface MoviesOnClickHandler {
        void onClick(MovieInfo movieInfo);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.movie_image_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieInfo movieInfo = mMovieInfos.get(position);
        URL posterImageUrl = NetworkUtils.getMoviePosterUrl(movieInfo.getPosterPath());
        Glide.with(mContext)
            .load(new GlideUrl(posterImageUrl))
            .apply(new RequestOptions()
                    .placeholder(R.drawable.loading_circle)
                    .error(R.drawable.stock_image_not_available))
            .into(holder.thumbnailView);
    }

    @Override
    public int getItemCount() {
        return mMovieInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView thumbnailView;

        public ViewHolder(View itemView) {
            super(itemView);
            thumbnailView = itemView.findViewById(R.id.poster_image_iv);
            thumbnailView.setContentDescription(mMovieInfos.get(getAdapterPosition()+1).getTitle());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mMovieInfos.get(adapterPosition));
        }
    }

    /**
     * Swaps the current list of MovieInfo objects with a new one,
     * used for when the network request is completed so the
     * poster images can be displayed
     *
     * @param newMovies List of MovieInfo objects to be displayed by adapter
     */
    public void changeData(List<MovieInfo> newMovies) {
        mMovieInfos = new ArrayList<>();
        mMovieInfos.addAll(newMovies);
        notifyDataSetChanged();

    }
}
