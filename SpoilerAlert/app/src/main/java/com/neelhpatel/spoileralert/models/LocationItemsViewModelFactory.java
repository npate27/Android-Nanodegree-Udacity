package com.neelhpatel.spoileralert.models;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.neelhpatel.spoileralert.database.AppDatabase;

public class LocationItemsViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mLocationId;

    public LocationItemsViewModelFactory(AppDatabase database, int locationId) {
        mDb = database;
        mLocationId = locationId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LocationItemsViewModel(mDb, mLocationId);
    }
}
