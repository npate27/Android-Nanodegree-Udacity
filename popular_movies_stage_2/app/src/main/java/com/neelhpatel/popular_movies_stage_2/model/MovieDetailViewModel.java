package com.neelhpatel.popular_movies_stage_2.model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.neelhpatel.popular_movies_stage_2.database.AppDatabase;

public class MovieDetailViewModel extends ViewModel {

    private final LiveData<MovieInfo> favorite;

    public MovieDetailViewModel(AppDatabase database, int favoriteId) {
        favorite = database.favoritesDao().loadFavoriteById(favoriteId);
    }

    public LiveData<MovieInfo> getFavorite() {
        return favorite;
    }
}
