package com.neelhpatel.popular_movies_stage_2.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
    private final List<TrailerInfo> mTrailerInfos;
    private final Context mContext;

    public TrailerInfoAdapter(List<TrailerInfo> trailerInfos, Context context) {
        mTrailerInfos = trailerInfos;
        mContext = context;
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public final TextView trailerTitleView;

        public ViewHolder(View itemView) {
            super(itemView);
            trailerTitleView = itemView.findViewById(R.id.trailer_title_tv);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String videoKey = mTrailerInfos.get(getAdapterPosition()).getKey();
            Uri uri = Uri.parse("http://www.youtube.com/watch?v=".concat(videoKey));
            mContext.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }

        @Override
        public boolean onLongClick(View v) {
            String videoKey = "http://www.youtube.com/watch?v=".concat(mTrailerInfos.get(getAdapterPosition()).getKey());
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, videoKey);
            sendIntent.setType("text/plain");
            mContext.startActivity(sendIntent);
            return true;
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
