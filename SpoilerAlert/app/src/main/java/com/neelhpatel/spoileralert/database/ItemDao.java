package com.neelhpatel.spoileralert.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.neelhpatel.spoileralert.models.ItemInfo;

import java.util.List;

@Dao
public interface ItemDao {

    @Query("SELECT * FROM items")
    LiveData<List<ItemInfo>> loadAllItems();

    @Query("SELECT * FROM items WHERE location_id=:location_id")
    LiveData<List<ItemInfo>> loadItemsByLocation(int location_id);

    @Query("SELECT * FROM items WHERE u_id =:itemId")
    LiveData<ItemInfo> loadItemById(int itemId);

    @Query("SELECT * FROM items WHERE expire_date < :todayDate")
    LiveData<List<ItemInfo>> loadItemsExpired(long todayDate);

    @Query("SELECT * FROM items WHERE expire_date BETWEEN :todayDateBegin AND :todayDateEnd")
    LiveData<List<ItemInfo>> loadItemsExpiringToday(long todayDateBegin, long todayDateEnd);

    @Query("SELECT * FROM items WHERE expire_date BETWEEN :tomorrowDateBegin AND :tomorrowDateEnd")
    LiveData<List<ItemInfo>> loadItemsExpiringTomorrow(long tomorrowDateBegin, long tomorrowDateEnd);

    @Query("SELECT * FROM items WHERE expire_date BETWEEN :todayDate AND :weekDate")
    LiveData<List<ItemInfo>> loadItemsExpiringInWeek(long todayDate, long weekDate);

    @Insert
    long insertItem(ItemInfo itemInfo);

    @Delete
    void deleteItem(ItemInfo itemInfo);

    @Update
    void updateItem(ItemInfo itemInfo);

}
