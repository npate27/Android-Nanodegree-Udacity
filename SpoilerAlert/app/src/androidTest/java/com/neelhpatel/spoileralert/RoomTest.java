package com.neelhpatel.spoileralert;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.neelhpatel.spoileralert.database.AppDatabase;
import com.neelhpatel.spoileralert.database.DbUtils;
import com.neelhpatel.spoileralert.models.ItemInfo;
import com.neelhpatel.spoileralert.models.LocationInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class RoomTest {

    public AppDatabase mDatabase;

    @Before
    public void initDb() throws Exception {
        mDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getContext(),
            AppDatabase.class)
            .build();
    }

    @Test
    public void insertAndDeleteAndGetLocationsWithoutUsers() {
        mDatabase.locationDao().insertLocation(new LocationInfo("Not Assigned"));
        LocationInfo locationInfo1 = new LocationInfo("Pantry Kitchen");
        LocationInfo locationInfo2 = new LocationInfo("Cooler Basement");
        LocationInfo locationInfo3 = new LocationInfo("Cooler Garage");
        mDatabase.locationDao().insertLocation(locationInfo1);
        mDatabase.locationDao().insertLocation(locationInfo2);
        mDatabase.locationDao().insertLocation(locationInfo3);

        List<LocationInfo> locations = mDatabase.locationDao().loadAllLocations();
        assertThat(locations.size(), is(4));
        LocationInfo location = locations.get(2);
        System.out.println(location.locationName);
        assertEquals("Cooler Basement", location.locationName);

        mDatabase.locationDao().deleteLocation(locationInfo2);
        locations = mDatabase.locationDao().loadAllLocations();
        System.out.println("RoomTest: " + locations.size());
        assertThat(locations.size(), is(4));
    }

    @Test
    public void addItemsAndGetByLocationId() {
        mDatabase.locationDao().insertLocation(new LocationInfo("Not Assigned"));
        LocationInfo locationInfo1 = new LocationInfo("Pantry Kitchen");
        LocationInfo locationInfo2 = new LocationInfo("Cooler Basement");
        LocationInfo locationInfo3 = new LocationInfo("Cooler Garage");
        mDatabase.locationDao().insertLocation(locationInfo1);
        mDatabase.locationDao().insertLocation(locationInfo2);
        mDatabase.locationDao().insertLocation(locationInfo3);

        Calendar ce1 = Calendar.getInstance();
        ce1.setTime(new Date());
        ce1.add(Calendar.DATE, 5);
        Date exp1 = ce1.getTime();

        Calendar cp1 = Calendar.getInstance();
        cp1.setTime(new Date());
        cp1.add(Calendar.DATE, -10);
        Date pur1 = cp1.getTime();

        ItemInfo itemInfo1 = new ItemInfo("Item#1", exp1, pur1, "", 2.30, "2 boxes", 2);

        Calendar ce2 = Calendar.getInstance();
        ce2.setTime(new Date());
        ce2.add(Calendar.DATE, 1);
        Date exp2 = ce2.getTime();

        Calendar cp2 = Calendar.getInstance();
        cp2.setTime(new Date());
        cp2.add(Calendar.DATE, -5);
        Date pur2 = cp2.getTime();

        ItemInfo itemInfo2 = new ItemInfo("Item#3", exp2, pur2, "", 2.30, "2 lbs", 2);

        Calendar ce3 = Calendar.getInstance();
        ce3.setTime(new Date());
        ce3.add(Calendar.DATE, 3);
        Date exp3 = ce3.getTime();

        Calendar cp3 = Calendar.getInstance();
        cp3.setTime(new Date());
        cp3.add(Calendar.DATE, -10);
        Date pur3 = cp3.getTime();

        ItemInfo itemInfo3 = new ItemInfo("Item#3", exp3, pur3, "", 2.30, "3 boxes", 3);
        ItemInfo itemInfo4 = new ItemInfo("Item#4", exp3, pur3, "", 2.30, "4 boxes");

        mDatabase.itemDao().insertItem(itemInfo1);
        mDatabase.itemDao().insertItem(itemInfo2);
        mDatabase.itemDao().insertItem(itemInfo3);
        mDatabase.itemDao().insertItem(itemInfo4);

        List<ItemInfo> items = mDatabase.itemDao().loadAllItems();
        assertThat(items.size(), is(4));

        items = mDatabase.itemDao().loadItemsByLocation(2);
        assertThat(items.size(), is(2));
    }

    @Test
    public void updateItemThatHasNoLocation() {
        mDatabase.locationDao().insertLocation(new LocationInfo("Not Assigned"));
        LocationInfo locationInfo1 = new LocationInfo("Pantry Kitchen");
        mDatabase.locationDao().insertLocation(locationInfo1);
        ItemInfo itemInfo1 = new ItemInfo("Item#1", new Date(), new Date(), "", 2.30, "4 boxes", 1);
        long itemId1 = mDatabase.itemDao().insertItem(itemInfo1);

        assertThat(itemInfo1.locationId, is(1));
        assertThat((int) itemId1, is(1));

        List<ItemInfo> items = mDatabase.itemDao().loadAllItems();
        ItemInfo itemInfo1New = items.get(0);
        itemInfo1New.setLocationId(2);
        mDatabase.itemDao().updateItem(itemInfo1New);
        items = mDatabase.itemDao().loadItemsByLocation(2);

        assertThat(items.size(), is(1));
        assertThat(items.get(0).locationId, is(2));
    }

    @Test
    public void updateLocationName() {
        mDatabase.locationDao().insertLocation(new LocationInfo("Not Assigned"));
        LocationInfo locationInfo1 = new LocationInfo("Pantry Kitchen");
        mDatabase.locationDao().insertLocation(locationInfo1);

        List<LocationInfo> locations = mDatabase.locationDao().loadAllLocations();
        LocationInfo locationInfo1New = locations.get(1);
        locationInfo1New.setLocationName("New location");
        mDatabase.locationDao().updateLocation(locationInfo1New);
        locations = mDatabase.locationDao().loadAllLocations();


        assertThat(locations.get(1).locationName, is("New location"));
    }

    //Delete item
    @Test
    public void deleteItemsAndLocations() {
        mDatabase.locationDao().insertLocation(new LocationInfo("Not Assigned"));
        LocationInfo locationInfo1 = new LocationInfo("Pantry Kitchen");
        LocationInfo locationInfo2 = new LocationInfo("Cooler Basement");
        LocationInfo locationInfo3 = new LocationInfo("Cooler Garage");
        mDatabase.locationDao().insertLocation(locationInfo1);
        mDatabase.locationDao().insertLocation(locationInfo2);
        mDatabase.locationDao().insertLocation(locationInfo3);

        Calendar ce1 = Calendar.getInstance();
        ce1.setTime(new Date());
        ce1.add(Calendar.DATE, 5);
        Date exp1 = ce1.getTime();

        Calendar cp1 = Calendar.getInstance();
        cp1.setTime(new Date());
        cp1.add(Calendar.DATE, -10);
        Date pur1 = cp1.getTime();

        ItemInfo itemInfo1 = new ItemInfo("Item#1", exp1, pur1, "", 2.30, "2 boxes", 2);

        Calendar ce2 = Calendar.getInstance();
        ce2.setTime(new Date());
        ce2.add(Calendar.DATE, 1);
        Date exp2 = ce2.getTime();

        Calendar cp2 = Calendar.getInstance();
        cp2.setTime(new Date());
        cp2.add(Calendar.DATE, -5);
        Date pur2 = cp2.getTime();

        ItemInfo itemInfo2 = new ItemInfo("Item#3", exp2, pur2, "", 2.30, "2 lbs", 2);

        Calendar ce3 = Calendar.getInstance();
        ce3.setTime(new Date());
        ce3.add(Calendar.DATE, 3);
        Date exp3 = ce3.getTime();

        Calendar cp3 = Calendar.getInstance();
        cp3.setTime(new Date());
        cp3.add(Calendar.DATE, -10);
        Date pur3 = cp3.getTime();

        ItemInfo itemInfo3 = new ItemInfo("Item#3", exp3, pur3, "", 2.30, "3 boxes", 3);
        ItemInfo itemInfo4 = new ItemInfo("Item#4", exp3, pur3, "", 2.30, "4 boxes");

        mDatabase.itemDao().insertItem(itemInfo1);
        mDatabase.itemDao().insertItem(itemInfo2);
        mDatabase.itemDao().insertItem(itemInfo3);
        mDatabase.itemDao().insertItem(itemInfo4);

        List<ItemInfo> items = mDatabase.itemDao().loadAllItems();
        ItemInfo itemInfo1New = items.get(3);
        mDatabase.itemDao().deleteItem(itemInfo1New);
        items = mDatabase.itemDao().loadAllItems();

        assertThat(items.size(), is(3));

        List<LocationInfo> locations = mDatabase.locationDao().loadAllLocations();
        LocationInfo locationInfo2New = locations.get(1);
        items = mDatabase.itemDao().loadItemsByLocation(2);
        if (items.size() > 0) {
//            for (ItemInfo item : items) {
//                    mDatabase.itemDao().deleteItem(item);
//            }
            for (ItemInfo item : items) {
                    item.setLocationId(1);
                    mDatabase.itemDao().updateItem(item);
            }
        }
        mDatabase.locationDao().deleteLocation(locationInfo2New);

        locations = mDatabase.locationDao().loadAllLocations();
        assertThat(locations.size(), is(3));

        items = mDatabase.itemDao().loadAllItems();
//        assertThat(items.size(), is(1));
        assertThat(items.size(), is(3));
    }

    @Test
    public void queryExpirations() {
        mDatabase.locationDao().insertLocation(new LocationInfo("Not Assigned"));
        LocationInfo locationInfo1 = new LocationInfo("Pantry Kitchen");
        LocationInfo locationInfo2 = new LocationInfo("Cooler Basement");
        LocationInfo locationInfo3 = new LocationInfo("Cooler Garage");
        mDatabase.locationDao().insertLocation(locationInfo1);
        mDatabase.locationDao().insertLocation(locationInfo2);
        mDatabase.locationDao().insertLocation(locationInfo3);

        Calendar ce1 = Calendar.getInstance();
        ce1.setTime(new Date());
        ce1.add(Calendar.DATE, -3);
        Date exp1 = ce1.getTime();

        Calendar cp1 = Calendar.getInstance();
        cp1.setTime(new Date());
        cp1.add(Calendar.DATE, -10);
        Date pur1 = cp1.getTime();

        ItemInfo itemInfo1 = new ItemInfo("Item#1", exp1, pur1, "", 2.31, "2 boxes", 2);

        Calendar ce2 = Calendar.getInstance();
        ce2.setTime(new Date());
        ce2.add(Calendar.DATE, 0);
        Date exp2 = ce2.getTime();

        Calendar cp2 = Calendar.getInstance();
        cp2.setTime(new Date());
        cp2.add(Calendar.DATE, -5);
        Date pur2 = cp2.getTime();

        ItemInfo itemInfo2 = new ItemInfo("Item#3", exp2, pur2, "", 2.32, "2 lbs", 2);

        Calendar ce3 = Calendar.getInstance();
        ce3.setTime(new Date());
        ce3.add(Calendar.DATE, 1);
        Date exp3 = ce3.getTime();

        Calendar cp3 = Calendar.getInstance();
        cp3.setTime(new Date());
        cp3.add(Calendar.DATE, -10);
        Date pur3 = cp3.getTime();

        ItemInfo itemInfo3 = new ItemInfo("Item#3", exp3, pur3, "", 2.33, "3 boxes", 3);

        Calendar ce4 = Calendar.getInstance();
        ce4.setTime(new Date());
        ce4.add(Calendar.DATE, 3);
        Date exp4 = ce4.getTime();

        Calendar cp4 = Calendar.getInstance();
        cp4.setTime(new Date());
        cp4.add(Calendar.DATE, -10);
        Date pur4 = cp4.getTime();
        ItemInfo itemInfo4 = new ItemInfo("Item#4", exp4, pur4, "", 2.34, "4 boxes");

        mDatabase.itemDao().insertItem(itemInfo1);
        mDatabase.itemDao().insertItem(itemInfo2);
        mDatabase.itemDao().insertItem(itemInfo3);
        mDatabase.itemDao().insertItem(itemInfo4);

        GregorianCalendar cTodayBeginning = new GregorianCalendar();
        cTodayBeginning.set(Calendar.HOUR_OF_DAY, 0);
        cTodayBeginning.set(Calendar.MINUTE, 0);
        cTodayBeginning.set(Calendar.SECOND, 0);

        GregorianCalendar cTodayEnd = (GregorianCalendar) cTodayBeginning.clone();
        cTodayEnd.add(Calendar.DATE, 1);
        List<ItemInfo> items = mDatabase.itemDao().loadItemsExpiringToday(cTodayBeginning.getTimeInMillis(), cTodayEnd.getTimeInMillis());
        assertThat(items.size(), is(1));

        GregorianCalendar cTomorrowBeginning = new GregorianCalendar();
        cTomorrowBeginning.set(Calendar.HOUR_OF_DAY, 0);
        cTomorrowBeginning.set(Calendar.MINUTE, 0);
        cTomorrowBeginning.set(Calendar.SECOND, 0);
        cTomorrowBeginning.add(Calendar.DATE, 1);

        GregorianCalendar cTomorrowEnding = (GregorianCalendar) cTomorrowBeginning.clone();
        cTomorrowEnding.add(Calendar.DATE, 1);

        items = mDatabase.itemDao().loadItemsExpiringTomorrow(cTomorrowBeginning.getTimeInMillis(), cTomorrowEnding.getTimeInMillis());
        assertThat(items.size(), is(1));

        GregorianCalendar cWeekStarting = new GregorianCalendar();
        cWeekStarting.set(Calendar.HOUR_OF_DAY, 0);
        cWeekStarting.set(Calendar.MINUTE, 0);
        cWeekStarting.set(Calendar.SECOND, 0);
        cWeekStarting.add(Calendar.DATE, 2);

        GregorianCalendar cWeekEnding = (GregorianCalendar) cWeekStarting.clone();
        cWeekEnding.add(Calendar.DATE, 7);
        items = mDatabase.itemDao().loadItemsExpiringInWeek(cWeekStarting.getTimeInMillis(), cWeekEnding.getTimeInMillis());
        assertThat(items.size(), is(1));
    }

    @After
    public void closeDb() throws Exception {
        if(mDatabase != null){
            mDatabase.close();
        }
    }
}
