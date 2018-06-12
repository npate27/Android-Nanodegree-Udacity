package com.neelhpatel.popular_movies_stage_2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neelhpatel.popular_movies_stage_2.R;
import com.neelhpatel.popular_movies_stage_2.model.TrailerInfo;

import java.util.List;

public class TrailerInfoAdapter extends RecyclerView.Adapter<TrailerInfoAdapter.ViewHolder> {
    private final Context mContext;
    private final List<TrailerInfo> mTrailerInfos;
    private final TrailersOnClickHandler mClickHandler;


    public TrailerInfoAdapter(Context context, List<TrailerInfo> trailerInfos, TrailersOnClickHandler clickHandler) {
        mContext = context;
        mTrailerInfos = trailerInfos;
        mClickHandler = clickHandler;
    }

    public interface TrailersOnClickHandler {
        void onClick(TrailerInfo trailerInfo);
    }

    @NonNull
    @Override
    public TrailerInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.movie_trailer_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerInfoAdapter.ViewHolder holder, int position) {
        TrailerInfo trailerInfo = mTrailerInfos.get(position);
        holder.trailerTitleView.setText(trailerInfo.getName());
    }

    @Override
    public int getItemCount() {
        return mTrailerInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView trailerTitleView;

        public ViewHolder(View itemView) {
            super(itemView);
            trailerTitleView = itemView.findViewById(R.id.trailer_title_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mTrailerInfos.get(adapterPosition));
        }
    }

    /**
     * Swaps the current list of TrailerInfo objects with a new one,
     * used for when the network request is completed so the
     * trailers can be displayed
     *
     * @param trailers List of TrailerInfo objects to be displayed by adapter
     */
    public void changeData(List<TrailerInfo> trailers) {
        mTrailerInfos.clear();
        mTrailerInfos.addAll(trailers);
        notifyDataSetChanged();

    }
}
