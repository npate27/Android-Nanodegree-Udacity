package com.neelhpatel.spoileralert.models;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.neelhpatel.spoileralert.database.AppDatabase;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ExpirationsViewModel extends AndroidViewModel {

    private final LiveData<List<ItemInfo>> expired;
    private final LiveData<List<ItemInfo>> todayExpired;
    private final LiveData<List<ItemInfo>> tomorrowExpired;
    private final LiveData<List<ItemInfo>> weekExpired;

    public ExpirationsViewModel(@NonNull Application application) {
        super(application);
        AppDatabase database = AppDatabase.getsInstance(application.getApplicationContext());

        GregorianCalendar cTodayBeginning = new GregorianCalendar();
        cTodayBeginning.set(Calendar.HOUR_OF_DAY, 0);
        cTodayBeginning.set(Calendar.MINUTE, 0);
        cTodayBeginning.set(Calendar.SECOND, 0);

        GregorianCalendar cTodayEnd = (GregorianCalendar) cTodayBeginning.clone();
        cTodayEnd.add(Calendar.DATE, 1);

        GregorianCalendar cTomorrowBeginning = new GregorianCalendar();
        cTomorrowBeginning.set(Calendar.HOUR_OF_DAY, 0);
        cTomorrowBeginning.set(Calendar.MINUTE, 0);
        cTomorrowBeginning.set(Calendar.SECOND, 0);
        cTomorrowBeginning.add(Calendar.DATE, 1);

        GregorianCalendar cTomorrowEnding = (GregorianCalendar) cTomorrowBeginning.clone();
        cTomorrowEnding.add(Calendar.DATE, 1);

        GregorianCalendar cWeekStarting = new GregorianCalendar();
        cWeekStarting.set(Calendar.HOUR_OF_DAY, 0);
        cWeekStarting.set(Calendar.MINUTE, 0);
        cWeekStarting.set(Calendar.SECOND, 0);
        cWeekStarting.add(Calendar.DATE, 2);

        GregorianCalendar cWeekEnding = (GregorianCalendar) cWeekStarting.clone();
        cWeekEnding.add(Calendar.DATE, 7);

        expired = database.itemDao().loadItemsExpired(cTodayBeginning.getTimeInMillis());
        todayExpired = database.itemDao().loadItemsExpiringToday(cTodayBeginning.getTimeInMillis(), cTodayEnd.getTimeInMillis());
        tomorrowExpired = database.itemDao().loadItemsExpiringTomorrow(cTomorrowBeginning.getTimeInMillis(), cTomorrowEnding.getTimeInMillis());
        weekExpired = database.itemDao().loadItemsExpiringInWeek(cWeekStarting.getTimeInMillis(), cWeekEnding.getTimeInMillis());
    }

    public LiveData<List<ItemInfo>> getExpired() {
        return expired;
    }

    public LiveData<List<ItemInfo>> getTodayExpired() {
        return todayExpired;
    }

    public LiveData<List<ItemInfo>> getTomorrowExpired() {
        return tomorrowExpired;
    }

    public LiveData<List<ItemInfo>> getWeekExpired() {
        return weekExpired;
    }
}