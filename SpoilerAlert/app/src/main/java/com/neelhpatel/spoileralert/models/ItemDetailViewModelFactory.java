package com.neelhpatel.spoileralert.models;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.neelhpatel.spoileralert.database.AppDatabase;

public class ItemDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mItemId;

    public ItemDetailViewModelFactory(AppDatabase database, int itemId) {
        mDb = database;
        mItemId = itemId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ItemDetailViewModel(mDb, mItemId);
    }
}
