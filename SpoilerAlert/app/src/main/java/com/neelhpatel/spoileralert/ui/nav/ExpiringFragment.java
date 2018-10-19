package com.neelhpatel.spoileralert.ui.nav;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neelhpatel.spoileralert.R;
import com.neelhpatel.spoileralert.adapters.ItemInfoAdapter;
import com.neelhpatel.spoileralert.database.AppDatabase;
import com.neelhpatel.spoileralert.models.ExpirationsViewModel;
import com.neelhpatel.spoileralert.models.ItemInfo;
import com.xwray.groupie.ExpandableGroup;
import com.xwray.groupie.Group;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Section;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ExpiringFragment extends Fragment {

    //TODO: New instance stuff?
    public RecyclerView itemsRecyclerView;
    private static ItemInfoAdapter mItemInfoAdapter;
    private static List<Section> mSectionInfos = new ArrayList<>();
    GroupAdapter groupAdapter;
    GridLayoutManager layoutManager;
    int count = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_expiring, container, false);
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        groupAdapter = new GroupAdapter();
        groupAdapter.setSpanCount(12);
        layoutManager = new GridLayoutManager(getContext(), groupAdapter.getSpanCount());
        layoutManager.setSpanSizeLookup(groupAdapter.getSpanSizeLookup());

        itemsRecyclerView = view.findViewById(R.id.items_rv);
        itemsRecyclerView.setLayoutManager(layoutManager);
        itemsRecyclerView.setAdapter(groupAdapter);

        populateAdapter();


    }

    private void populateAdapter() {

        List<Section> sectionInfos = new ArrayList<>();
        sectionInfos.add(null);
        sectionInfos.add(null);
        sectionInfos.add(null);
        sectionInfos.add(null);

        Section carouselSection = new Section(new HeaderItem(R.string.past_expiration_title));
        carouselSection.setHideWhenEmpty(true);
        CarouselItemDecoration carouselDecoration = new CarouselItemDecoration(ContextCompat.getColor(getContext(), R.color.white), getResources().getDimensionPixelSize(R.dimen.cardview_margin));
        GroupAdapter carouselAdapter = new GroupAdapter();

        ExpirationsViewModel expirationViewModel = ViewModelProviders.of(this).get(ExpirationsViewModel.class);
        expirationViewModel.getExpired().observe(this, itemInfos -> {
            if (itemInfos.size() > 0) {
                for(ItemInfo itemInfo: itemInfos) {
                    carouselAdapter.add(new CarouselCardItem(itemInfo, AppDatabase.getsInstance(getContext())));
                }
                Group group = new CarouselGroup(carouselDecoration, carouselAdapter);
                carouselSection.add(group);
                sectionInfos.set(0, carouselSection);
            }
            checkAndSetAdapter(sectionInfos);
        });

        Section carouselSection1 = new Section(new HeaderItem(R.string.expires_today_title));
        carouselSection1.setHideWhenEmpty(true);
        GroupAdapter carouselAdapter1 = new GroupAdapter();

        ExpirationsViewModel expirationTodayViewModel = ViewModelProviders.of(this).get(ExpirationsViewModel.class);
        expirationTodayViewModel.getTodayExpired().observe(this, itemInfos -> {
            if (itemInfos.size() > 0) {
                for(ItemInfo itemInfo: itemInfos) {
                    carouselAdapter1.add(new CarouselCardItem(itemInfo, AppDatabase.getsInstance(getContext())));
                }
                Group group = new CarouselGroup(carouselDecoration, carouselAdapter1);
                carouselSection1.add(group);
                sectionInfos.set(1, carouselSection1);
            }
            checkAndSetAdapter(sectionInfos);
        });

        Section carouselSection2 = new Section(new HeaderItem(R.string.expires_tomorrow_title));
        carouselSection2.setHideWhenEmpty(true);
        GroupAdapter carouselAdapter2 = new GroupAdapter();

        ExpirationsViewModel expirationTomorrowViewModel = ViewModelProviders.of(this).get(ExpirationsViewModel.class);
        expirationTomorrowViewModel.getTomorrowExpired().observe(this, itemInfos -> {
            if (itemInfos.size() > 0) {
                for(ItemInfo itemInfo: itemInfos) {
                    carouselAdapter2.add(new CarouselCardItem(itemInfo, AppDatabase.getsInstance(getContext())));
                }
                Group group = new CarouselGroup(carouselDecoration, carouselAdapter2);
                carouselSection2.add(group);
                sectionInfos.set(2, carouselSection2);
            }
            checkAndSetAdapter(sectionInfos);
        });

        Section carouselSection3 = new Section(new HeaderItem(R.string.expires_today_title));
        carouselSection3.setHideWhenEmpty(true);
        GroupAdapter carouselAdapter3 = new GroupAdapter();

        ExpirationsViewModel expirationWeekViewModel = ViewModelProviders.of(this).get(ExpirationsViewModel.class);
        expirationWeekViewModel.getTodayExpired().observe(this, itemInfos -> {
            if (itemInfos.size() > 0) {
                for(ItemInfo itemInfo: itemInfos) {
                    carouselAdapter3.add(new CarouselCardItem(itemInfo, AppDatabase.getsInstance(getContext())));
                }
                Group group = new CarouselGroup(carouselDecoration, carouselAdapter3);
                carouselSection3.add(group);
                sectionInfos.set(3, carouselSection3);
            }
            checkAndSetAdapter(sectionInfos);
        });
    }


    public void checkAndSetAdapter(List<Section> sectionInfos) {
        count += 1;
        if(count == 4) {
            sectionInfos.removeAll(Collections.singleton(null));
            groupAdapter.addAll(sectionInfos);
            mSectionInfos = sectionInfos;
            itemsRecyclerView.setAdapter(groupAdapter);

        }
    }

}
