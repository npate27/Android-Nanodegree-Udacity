package com.neelhpatel.bakingapp.fragments;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neelhpatel.bakingapp.R;
import com.neelhpatel.bakingapp.adapters.StepListAdapter;
import com.neelhpatel.bakingapp.model.StepInfo;

import java.util.ArrayList;

public class StepsFragment extends Fragment implements StepListAdapter.StepsClickHandler {
    private static final String STEPS_KEY = "steps_key";
    private ArrayList<StepInfo> mStepInfos;
    OnStepClickListener mCallback;
    RecyclerView recyclerView;

    public interface OnStepClickListener {
        void onStepSelected(StepInfo stepInfo);
    }

    public StepsFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    public static StepsFragment newInstance(ArrayList<StepInfo> stepInfos){
        StepsFragment fragment = new StepsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STEPS_KEY, stepInfos);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_master_step_list, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState != null) {
            mStepInfos = savedInstanceState.getParcelableArrayList(STEPS_KEY);
        } else {
            mStepInfos = getArguments().getParcelableArrayList(STEPS_KEY);
        }
        recyclerView = view.findViewById(R.id.steps_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        StepListAdapter mAdapter = new StepListAdapter(mStepInfos, this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(StepInfo stepInfo) {
        if (stepInfo.getId() < 0) {
            mCallback.onStepSelected(null);
        } else {
            mCallback.onStepSelected(stepInfo);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(STEPS_KEY, mStepInfos);
    }
}
