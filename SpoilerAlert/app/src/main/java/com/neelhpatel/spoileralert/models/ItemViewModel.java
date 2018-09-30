package com.neelhpatel.spoileralert.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.neelhpatel.spoileralert.database.AppDatabase;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {

    public final LiveData<List<ItemInfo>> items;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getsInstance(application.getApplicationContext());
        items = database.itemDao().loadAllItems();
    }

    public LiveData<List<ItemInfo>> getItems() {
        return items;
    }
}
