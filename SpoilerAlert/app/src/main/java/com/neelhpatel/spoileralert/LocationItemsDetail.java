package com.neelhpatel.spoileralert;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.neelhpatel.spoileralert.adapters.ItemInfoAdapter;
import com.neelhpatel.spoileralert.database.AppDatabase;
import com.neelhpatel.spoileralert.models.ItemInfo;

import com.neelhpatel.spoileralert.models.LocationItemsViewModel;
import com.neelhpatel.spoileralert.models.LocationItemsViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import static com.neelhpatel.spoileralert.ui.nav.ItemsFragment.ITEM_KEY;
import static com.neelhpatel.spoileralert.ui.nav.LocationsFragment.LOCATION_ID_KEY;

public class LocationItemsDetail extends AppCompatActivity implements ItemInfoAdapter.ItemsOnClickHandler {

    private static List<ItemInfo> mItemInfos = new ArrayList<>();
    private static ItemInfoAdapter mItemInfoAdapter;
    private int mLocationId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_items);
        Intent intent = getIntent();
        mLocationId = intent.getIntExtra(LOCATION_ID_KEY, 0);
        RecyclerView itemRecyclerView = findViewById(R.id.items_rv);
        itemRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        fetchItems();
        mItemInfoAdapter = new ItemInfoAdapter(this, mItemInfos,this);
        itemRecyclerView.setAdapter(mItemInfoAdapter);
    }


    private void fetchItems() {
        AppDatabase mDb = AppDatabase.getsInstance(this);
        LocationItemsViewModelFactory factory = new LocationItemsViewModelFactory(mDb, mLocationId);
        final LocationItemsViewModel viewModel = ViewModelProviders.of(this, factory).get(LocationItemsViewModel.class);
        viewModel.getItemsByLocation().observe(this, itemInfos -> {
            mItemInfos = itemInfos;
            mItemInfoAdapter.changeData(mItemInfos);
        });
    }

    @Override
    public void onClick(ItemInfo itemInfo) {
        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra(ITEM_KEY, itemInfo);
        startActivity(intent);
    }
}
