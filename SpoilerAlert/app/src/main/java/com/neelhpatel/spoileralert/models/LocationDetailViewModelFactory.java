package com.neelhpatel.spoileralert.models;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.neelhpatel.spoileralert.database.AppDatabase;

public class LocationDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mLocationId;

    public LocationDetailViewModelFactory(AppDatabase database, int locationId) {
        mDb = database;
        mLocationId = locationId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LocationDetailViewModel(mDb, mLocationId);
    }
}
