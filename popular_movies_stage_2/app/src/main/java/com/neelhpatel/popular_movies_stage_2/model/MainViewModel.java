package com.neelhpatel.popular_movies_stage_2.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.neelhpatel.popular_movies_stage_2.database.AppDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final LiveData<List<MovieInfo>> favorites;

    public MainViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getsInstance(this.getApplication());
        favorites = database.favoritesDao().loadAllFavorites();
    }

    public LiveData<List<MovieInfo>> getFavorites() {
        return favorites;
    }
}
