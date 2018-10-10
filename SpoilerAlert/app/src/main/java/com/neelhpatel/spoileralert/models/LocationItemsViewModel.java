package com.neelhpatel.spoileralert.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.neelhpatel.spoileralert.database.AppDatabase;

import java.util.List;

public class LocationItemsViewModel  extends ViewModel {

    public final LiveData<List<ItemInfo>> items;

    public LocationItemsViewModel(AppDatabase database, int locationId) {
        items = database.itemDao().loadItemsByLocation(locationId);
    }

    public LiveData<List<ItemInfo>> getItemsByLocation() {
        return items;
    }
}
