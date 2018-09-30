package com.neelhpatel.spoileralert.database;

import android.content.Context;

import com.neelhpatel.spoileralert.models.ItemInfo;
import com.neelhpatel.spoileralert.models.LocationInfo;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class DbUtils {
    public static void deleteLocation(Context context, LocationInfo locationInfo) {
//        AppDatabase mDb = AppDatabase.getsInstance(context);
//        List<ItemInfo> items = mDb.itemDao().loadItemsByLocation(locationInfo.getId());
//        if (items.size() > 0) {
//            //TODO:Notify user with confirmation?
//            boolean deleteAll = false;
//            if(deleteAll) {
//                for (ItemInfo item : items) {
//                    mDb.itemDao().deleteItem(item);
//                }
//            } else {
//                for (ItemInfo item : items) {
//                    //TODO: make default const for unassigned location
//                    item.setLocationId(1);
//                    mDb.itemDao().updateItem(item);
//                }
//            }
//        }
//        mDb.locationDao().deleteLocation(locationInfo);
//    }
//
//    public static List<ItemInfo> getExpiredItems(Context context) {
//        AppDatabase mDb = AppDatabase.getsInstance(context);
//        GregorianCalendar cTodayBeginning = new GregorianCalendar();
//        cTodayBeginning.set(Calendar.HOUR_OF_DAY, 0);
//        cTodayBeginning.set(Calendar.MINUTE, 0);
//        cTodayBeginning.set(Calendar.SECOND, 0);
//        return mDb.itemDao().loadItemsExpired(cTodayBeginning.getTimeInMillis());
//
//    }
//    public static List<ItemInfo> getTodayExpiringItems(Context context) {
//        AppDatabase mDb = AppDatabase.getsInstance(context);
//        GregorianCalendar cTodayBeginning = new GregorianCalendar();
//        cTodayBeginning.set(Calendar.HOUR_OF_DAY, 0);
//        cTodayBeginning.set(Calendar.MINUTE, 0);
//        cTodayBeginning.set(Calendar.SECOND, 0);
//
//        GregorianCalendar cTodayEnd = (GregorianCalendar) cTodayBeginning.clone();
//        cTodayEnd.add(Calendar.DATE, 1);
//
//        return mDb.itemDao().loadItemsExpiringToday(cTodayBeginning.getTimeInMillis(), cTodayEnd.getTimeInMillis());
//
//    }
//    public static List<ItemInfo> getTomorrowExpiringItems(Context context) {
//        AppDatabase mDb = AppDatabase.getsInstance(context);
//        GregorianCalendar cTomorrowBeginning = new GregorianCalendar();
//        cTomorrowBeginning.set(Calendar.HOUR_OF_DAY, 0);
//        cTomorrowBeginning.set(Calendar.MINUTE, 0);
//        cTomorrowBeginning.set(Calendar.SECOND, 0);
//        cTomorrowBeginning.add(Calendar.DATE, 1);
//
//        GregorianCalendar cTomorrowEnding = (GregorianCalendar) cTomorrowBeginning.clone();
//        cTomorrowEnding.add(Calendar.DATE, 1);
//
//        return mDb.itemDao().loadItemsExpiringTomorrow(cTomorrowBeginning.getTimeInMillis(), cTomorrowEnding.getTimeInMillis());
//
//    }
//    public static List<ItemInfo> getWeekExpiringItems(Context context) {
//        AppDatabase mDb = AppDatabase.getsInstance(context);
//        GregorianCalendar cWeekStarting = new GregorianCalendar();
//        cWeekStarting.set(Calendar.HOUR_OF_DAY, 0);
//        cWeekStarting.set(Calendar.MINUTE, 0);
//        cWeekStarting.set(Calendar.SECOND, 0);
//        cWeekStarting.add(Calendar.DATE, 2);
//
//        GregorianCalendar cWeekEnding = (GregorianCalendar) cWeekStarting.clone();
//        cWeekEnding.add(Calendar.DATE, 7);
//        return mDb.itemDao().loadItemsExpiringInWeek(cWeekStarting.getTimeInMillis(), cWeekEnding.getTimeInMillis());
    }
}
