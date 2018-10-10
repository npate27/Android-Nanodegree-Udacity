package com.neelhpatel.spoileralert.models;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.neelhpatel.spoileralert.R;
import com.neelhpatel.spoileralert.database.AppDatabase;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.text.SimpleDateFormat;

public class ItemViewHolder extends ChildViewHolder {
    public Context mContext;
    public TextView itemTitleTv;
    public TextView itemLocationNameTv;
    public TextView itemExpirationTv;
    public ImageView itemIv;
    public ImageButton editBtn;
    public ImageButton deleteBtn;

    public ItemViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        itemTitleTv = itemView.findViewById(R.id.item_title_tv);
        itemLocationNameTv = itemView.findViewById(R.id.item_location_tv);
        itemExpirationTv = itemView.findViewById(R.id.item_expiration_tv);
        itemIv = itemView.findViewById(R.id.thumbnail);
        editBtn = itemView.findViewById(R.id.edit_item_btn);
        deleteBtn = itemView.findViewById(R.id.delete_item_btn);
    }

    public void onBind(ItemInfo itemInfo) {
        itemTitleTv.setText(itemInfo.getItemName());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        itemExpirationTv.setText(dateFormat.format(itemInfo.getExpireDate()));
        //TODO:FIX Binding locs

        AppDatabase db = AppDatabase.getsInstance(mContext);
        Observer observer = (Observer<LocationInfo>) locationInfo -> itemLocationNameTv.setText(locationInfo.getLocationName());
        db.locationDao().loadLocationById(itemInfo.getLocationId()).observeForever(observer);
        db.locationDao().loadLocationById(itemInfo.getLocationId()).removeObserver(observer);
    }
}
