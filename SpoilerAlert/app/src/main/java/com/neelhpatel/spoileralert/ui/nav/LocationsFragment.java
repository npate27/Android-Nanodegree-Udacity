package com.neelhpatel.spoileralert.ui.nav;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neelhpatel.spoileralert.LocationItemsDetail;
import com.neelhpatel.spoileralert.R;
import com.neelhpatel.spoileralert.adapters.LocationInfoAdapter;
import com.neelhpatel.spoileralert.models.LocationInfo;
import com.neelhpatel.spoileralert.models.LocationViewModel;

import java.util.ArrayList;
import java.util.List;

public class LocationsFragment extends Fragment implements LocationInfoAdapter.LocationsOnClickHandler {

    public static final String LOCATION_ID_KEY = "location_id_key";
    //TODO: New instance stuff?
    private static List<LocationInfo> mLocationInfos = new ArrayList<>();
    private static LocationInfoAdapter mLocationInfoAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_locations, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView locationRecyclerView = view.findViewById(R.id.locations_rv);
        locationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fetchLocations();
        mLocationInfoAdapter = new LocationInfoAdapter(mLocationInfos,this);
        locationRecyclerView.setAdapter(mLocationInfoAdapter);
    }

    private void fetchLocations() {
        LocationViewModel locationViewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        locationViewModel.getLocations().observe(this, locationInfos -> {
            mLocationInfos = locationInfos;
            mLocationInfoAdapter.changeData(mLocationInfos);
        });
    }

    @Override
    public void onClick(LocationInfo movieInfo, View v) {
        Activity activity = getActivity();
        Intent intent = new Intent(activity, LocationItemsDetail.class);
        intent.putExtra(LOCATION_ID_KEY, movieInfo.getId());
        Bundle options = ActivityOptionsCompat.makeScaleUpAnimation(
                v, 0, 0, v.getWidth(), v.getHeight()).toBundle();

        ActivityCompat.startActivity(activity, intent, options);
    }
}
