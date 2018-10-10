package com.neelhpatel.spoileralert.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neelhpatel.spoileralert.R;
import com.neelhpatel.spoileralert.models.LocationInfo;

import java.util.ArrayList;
import java.util.List;

public class LocationInfoAdapter extends RecyclerView.Adapter<LocationInfoAdapter.ViewHolder> {
    private List<LocationInfo> mLocationInfos;
    private final LocationsOnClickHandler mClickHandler;

    public LocationInfoAdapter(List<LocationInfo> locationInfos, LocationsOnClickHandler clickHandler) {
        mLocationInfos = locationInfos;
        mClickHandler = clickHandler;
    }

    public interface LocationsOnClickHandler {
        void onClick(LocationInfo movieInfo, View v);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_location, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocationInfo locationInfo = mLocationInfos.get(position);
        holder.locationNameTv.setText(locationInfo.getLocationName());
    }

    @Override
    public int getItemCount() {
        return mLocationInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView locationNameTv;

        public ViewHolder(View itemView) {
            super(itemView);
            locationNameTv = itemView.findViewById(R.id.location_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mLocationInfos.get(adapterPosition), v);
        }
    }

    /**
     * Swaps the current list of LocationInfo objects with a new one
     *
     * @param newLocations List of LocationInfo objects to be displayed by adapter
     */
    public void changeData(List<LocationInfo> newLocations) {
        mLocationInfos = new ArrayList<>();
        mLocationInfos.addAll(newLocations);
        notifyDataSetChanged();

    }
}