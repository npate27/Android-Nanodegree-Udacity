package com.neelhpatel.spoileralert.ui.nav;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neelhpatel.spoileralert.R;
import com.neelhpatel.spoileralert.database.AppDatabase;
import com.neelhpatel.spoileralert.database.AppExecutors;
import com.neelhpatel.spoileralert.models.ItemInfo;
import com.neelhpatel.spoileralert.models.LocationInfo;
import com.neelhpatel.spoileralert.models.LocationViewModel;

import java.util.List;

public class ExpiringFragment extends Fragment {

    //TODO: New instance stuff?

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_expiring, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final AppDatabase mDb = AppDatabase.getsInstance(getContext());
        LocationViewModel viewModel = ViewModelProviders.of(this).get(LocationViewModel.class);
        viewModel.getLocations().observe(this, new Observer<List<LocationInfo>>() {
            @Override
            public void onChanged(@Nullable List<LocationInfo> locationInfos) {
                Log.d("TAG", locationInfos.toString());
            }
        });

    }
}
