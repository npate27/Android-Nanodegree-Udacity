package com.neelhpatel.spoileralert.ui.nav;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neelhpatel.spoileralert.R;
import com.neelhpatel.spoileralert.adapters.ItemInfoAdapter;
import com.neelhpatel.spoileralert.database.AppDatabase;
import com.neelhpatel.spoileralert.models.ExpirationAdapter;
import com.neelhpatel.spoileralert.models.ExpirationGroup;
import com.neelhpatel.spoileralert.models.ExpirationsViewModel;
import com.neelhpatel.spoileralert.models.ItemInfo;
import com.neelhpatel.spoileralert.models.ItemViewModel;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;
import com.xwray.groupie.GroupAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class ExpiringFragment extends Fragment {

    //TODO: New instance stuff?
    public ExpirationAdapter adapter;
    public RecyclerView itemsRecyclerView;
    private static ItemInfoAdapter mItemInfoAdapter;
    private static List<ItemInfo> mItemInfos = new ArrayList<>();
    private List<ExpirationGroup> expirationList  = new ArrayList<>();
    public  List<ExpirationGroup> expirationList1  = new ArrayList<>();
    int count = 0;
    GroupAdapter groupAdapter = new GroupAdapter();
    GridLayoutManager layoutManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_expiring, container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        itemsRecyclerView = view.findViewById(R.id.items_rv);
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerView.ItemAnimator animator = itemsRecyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        expirationList1.add(null);
        expirationList1.add(null);
        expirationList1.add(null);
        expirationList1.add(null);
        fetchItems(expirationList);
        adapter = new ExpirationAdapter(expirationList);
        itemsRecyclerView.setAdapter(adapter);

    }

    private void fetchItems(List<ExpirationGroup> expirationList) {
        ExpirationsViewModel expirationViewModel = ViewModelProviders.of(this).get(ExpirationsViewModel.class);
        expirationViewModel.getExpired().observe(this, itemInfos -> {
            if(itemInfos.size() > 0) {
                expirationList1.set(0, new ExpirationGroup(getString(R.string.past_expiration_title), itemInfos));
            }
            checkAndSetAdapter(expirationList, expirationList1);
        });
        expirationViewModel.getTodayExpired().observe(this, itemInfos -> {
            if(itemInfos.size() > 0) {
                expirationList1.set(1, new ExpirationGroup(getString(R.string.expires_today_title), itemInfos));
            }
            checkAndSetAdapter(expirationList, expirationList1);
        });
        expirationViewModel.getTomorrowExpired().observe(this, itemInfos -> {
            if(itemInfos.size() > 0) {
                expirationList1.set(2, new ExpirationGroup(getString(R.string.expires_tomorrow_title), itemInfos));
            }
            checkAndSetAdapter(expirationList, expirationList1);
        });
        expirationViewModel.getWeekExpired().observe(this, itemInfos -> {
            if(itemInfos.size() > 0) {
                expirationList1.set(3, new ExpirationGroup(getString(R.string.expires_week_title), itemInfos));
            }
            checkAndSetAdapter(expirationList, expirationList1);
        });
    }

    public void checkAndSetAdapter(List<ExpirationGroup> expirationList, List<ExpirationGroup> expirationList1) {
        count += 1;
        if(count == 4) {
            expirationList1.removeAll(Collections.singleton(null));
            this.expirationList = expirationList1;
            itemsRecyclerView.setAdapter(new ExpirationAdapter(expirationList1));
        }
    }
}
