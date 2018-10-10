package com.neelhpatel.spoileralert.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "locations")
public class LocationInfo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "l_id")
    public int id;

    @ColumnInfo(name = "location_name")
    public String locationName;

    @Ignore
    public LocationInfo(String locationName) {
        this.locationName = locationName;
    }

    public LocationInfo(int id, String locationName) {
        this.id = id;
        this.locationName = locationName;
    }

    public int getId() {
        return id;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

}
