package com.neelhpatel.spoileralert.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.neelhpatel.spoileralert.models.LocationInfo;

import java.util.List;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM locations")
    LiveData<List<LocationInfo>> loadAllLocations();

    @Insert
    long insertLocation(LocationInfo locationInfo);

    @Delete
    void deleteLocation(LocationInfo locationInfo);

    @Update
    void updateLocation(LocationInfo locationInfo);
}
