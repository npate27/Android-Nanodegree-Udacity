package com.neelhpatel.spoileralert.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.neelhpatel.spoileralert.database.AppDatabase;

import java.util.List;

public class LocationViewModel extends AndroidViewModel {

    private final LiveData<List<LocationInfo>> locations;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getsInstance(application.getApplicationContext());
        locations = database.locationDao().loadAllLocations();
    }

    public LiveData<List<LocationInfo>> getLocations() {
        return locations;
    }
}
