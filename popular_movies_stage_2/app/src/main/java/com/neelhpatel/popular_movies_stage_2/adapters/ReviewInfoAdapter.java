package com.neelhpatel.popular_movies_stage_2.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neelhpatel.popular_movies_stage_2.R;
import com.neelhpatel.popular_movies_stage_2.model.ReviewInfo;

import java.util.List;

public class ReviewInfoAdapter extends RecyclerView.Adapter<ReviewInfoAdapter.ViewHolder> {
    private final List<ReviewInfo> mReviewInfos;

    public ReviewInfoAdapter(List<ReviewInfo> reviewInfos) {
        mReviewInfos = reviewInfos;
    }

    public interface ReviewsOnClickHandler {
        void onClick(ReviewInfo reviewInfo);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.movie_review_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewInfo reviewInfo = mReviewInfos.get(position);
        holder.reviewAuthorView.setText(reviewInfo.getAuthor());
        holder.reviewContentView.setText(reviewInfo.getTextContent());
    }

    @Override
    public int getItemCount() {
        return mReviewInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView reviewAuthorView;
        public final TextView reviewContentView;

        public ViewHolder(View itemView) {
            super(itemView);
            reviewAuthorView = itemView.findViewById(R.id.review_author_tv);
            reviewContentView = itemView.findViewById(R.id.review_content_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            ReviewInfo reviewInfo = mReviewInfos.get(getAdapterPosition());
        }
    }

    /**
     * Swaps the current list of ReviewInfo objects with a new one,
     * used for when the network request is completed so the
     * reviews can be displayed
     *
     * @param reviews List of ReviewInfo objects to be displayed by adapter
     */
    public void changeData(List<ReviewInfo> reviews) {
        mReviewInfos.clear();
        mReviewInfos.addAll(reviews);
        notifyDataSetChanged();

    }
}
