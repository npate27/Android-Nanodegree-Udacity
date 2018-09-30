package com.neelhpatel.spoileralert.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.neelhpatel.spoileralert.database.AppDatabase;

public class ItemDetailViewModel extends ViewModel {

    private final LiveData<ItemInfo> item;

    public ItemDetailViewModel(AppDatabase database, int itemId) {
        item = database.itemDao().loadItemById(itemId);
    }

    public LiveData<ItemInfo> getItem() {
        return item;
    }
}
