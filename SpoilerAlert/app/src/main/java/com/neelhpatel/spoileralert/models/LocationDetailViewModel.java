package com.neelhpatel.spoileralert.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.neelhpatel.spoileralert.database.AppDatabase;

import java.util.List;

public class LocationDetailViewModel extends ViewModel {

    private final LiveData<LocationInfo> location;

    public LocationDetailViewModel(AppDatabase database, int locationId) {
        location = database.locationDao().loadLocationById(locationId);
    }

    public LiveData<LocationInfo> getLocation() {
        return location;
    }
}
