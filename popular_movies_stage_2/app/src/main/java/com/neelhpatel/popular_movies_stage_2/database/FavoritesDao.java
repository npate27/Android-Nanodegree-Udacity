package com.neelhpatel.popular_movies_stage_2.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.neelhpatel.popular_movies_stage_2.model.MovieInfo;

import java.util.List;

@Dao
public interface FavoritesDao {

    @Query("SELECT * FROM favorites")
    LiveData<List<MovieInfo>> loadAllFavorites();

    @Insert
    void insertFavorite(MovieInfo movieInfo);

    @Delete
    void deleteFavorite(MovieInfo movieInfo);

    @Query("SELECT * FROM favorites WHERE id = :id")
    LiveData<MovieInfo> loadFavoriteById(int id);
}
