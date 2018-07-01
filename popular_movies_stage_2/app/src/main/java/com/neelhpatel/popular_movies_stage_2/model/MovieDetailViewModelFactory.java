package com.neelhpatel.popular_movies_stage_2.model;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

@SuppressWarnings("ALL")
public class MovieDetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final AppDatabase mDb;
    private final int mFavoriteId;

    public MovieDetailViewModelFactory(AppDatabase database, int favoriteId) {
        mDb = database;
        mFavoriteId = favoriteId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
       return (T) new MovieDetailViewModel(mDb, mFavoriteId);
    }
}
