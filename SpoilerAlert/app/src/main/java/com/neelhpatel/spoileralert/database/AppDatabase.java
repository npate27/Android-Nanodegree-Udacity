package com.neelhpatel.spoileralert.database;


import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.neelhpatel.spoileralert.models.ItemInfo;
import com.neelhpatel.spoileralert.models.LocationInfo;

import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {ItemInfo.class, LocationInfo.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "spoilerDb";
    private static AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new db instance");
                sInstance = buildDatabase(context);
            }
        }
        Log.d(LOG_TAG, "Getting the db instance");
        return sInstance;
    }

    public static AppDatabase buildDatabase(final Context context) {
       return Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME)
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    sInstance.locationDao().insertLocation(new LocationInfo("Unassigned"));
                                }
                            });
                        }
                    })
                    .build();
            }

    public abstract ItemDao itemDao();
    public abstract LocationDao locationDao();
}