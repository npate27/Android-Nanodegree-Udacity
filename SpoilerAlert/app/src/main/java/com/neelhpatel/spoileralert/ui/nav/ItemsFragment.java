package com.neelhpatel.spoileralert.ui.nav;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neelhpatel.spoileralert.ItemDetailActivity;
import com.neelhpatel.spoileralert.ModifyItemActivity;
import com.neelhpatel.spoileralert.R;
import com.neelhpatel.spoileralert.adapters.ItemInfoAdapter;
import com.neelhpatel.spoileralert.adapters.LocationInfoAdapter;
import com.neelhpatel.spoileralert.models.ItemInfo;
import com.neelhpatel.spoileralert.models.ItemViewModel;
import com.neelhpatel.spoileralert.models.LocationInfo;
import com.neelhpatel.spoileralert.models.LocationViewModel;

import java.util.ArrayList;
import java.util.List;

public class ItemsFragment extends Fragment implements ItemInfoAdapter.ItemsOnClickHandler {

    public final static String ITEM_KEY = "item_key";

    //TODO: New instance stuff?
    private static List<ItemInfo> mItemInfos = new ArrayList<>();
    private static ItemInfoAdapter mItemInfoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_items, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView itemsRecyclerView = view.findViewById(R.id.items_rv);
        itemsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        fetchItems();
        mItemInfoAdapter = new ItemInfoAdapter(getContext(), mItemInfos,this);
        itemsRecyclerView.setAdapter(mItemInfoAdapter);
    }

    private void fetchItems() {
        ItemViewModel itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        itemViewModel.getItems().observe(this, itemInfos -> {
            mItemInfos = itemInfos;
            mItemInfoAdapter.changeData(mItemInfos);
        });
    }

    @Override
    public void onClick(ItemInfo itemInfo) {
        Intent intent = new Intent(getContext(), ItemDetailActivity.class);
        intent.putExtra(ITEM_KEY, itemInfo);
        startActivity(intent);
    }
}
