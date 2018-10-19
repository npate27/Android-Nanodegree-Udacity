package com.neelhpatel.spoileralert.ui.nav;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;

import com.neelhpatel.spoileralert.R;
import com.neelhpatel.spoileralert.database.AppDatabase;
import com.neelhpatel.spoileralert.databinding.ItemCardsBinding;
import com.neelhpatel.spoileralert.models.ItemInfo;
import com.neelhpatel.spoileralert.models.LocationInfo;
import com.xwray.groupie.databinding.BindableItem;

import java.text.SimpleDateFormat;

public class CardItem extends BindableItem<ItemCardsBinding> {
    private ItemInfo itemInfo;
    private AppDatabase db;

    public CardItem(ItemInfo itemInfo, AppDatabase db) {
        this.itemInfo = itemInfo;
        this.db = db;
    }

    @Override public int getLayout() {
        return R.layout.item_cards;
    }

    @Override public void bind(@NonNull ItemCardsBinding viewBinding, int position) {
        viewBinding.itemTitleTv.setText(itemInfo.getItemName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        viewBinding.itemExpirationTv.setText(dateFormat.format(itemInfo.getExpireDate()));
        //TODO:FIX Binding locs

        Observer observer = (Observer<LocationInfo>) locationInfo -> viewBinding.itemLocationTv.setText(locationInfo.getLocationName());
        db.locationDao().loadLocationById(itemInfo.getLocationId()).observeForever(observer);
        db.locationDao().loadLocationById(itemInfo.getLocationId()).removeObserver(observer);
    }

}
