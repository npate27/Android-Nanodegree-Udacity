package com.neelhpatel.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neelhpatel.bakingapp.R;
import com.neelhpatel.bakingapp.model.StepInfo;

public class StepFragment extends Fragment {
    private static final String STEP_KEY = "step_key";
    private StepInfo mStepInfo;

    public StepFragment() {}

    public static StepFragment newInstance(StepInfo stepInfo){
        StepFragment fragment = new StepFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(STEP_KEY, stepInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_step_cooking, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState != null) {
            mStepInfo = savedInstanceState.getParcelable(STEP_KEY);
        } else {
            mStepInfo = getArguments().getParcelable(STEP_KEY);
        }
        TextView instructionStep = view.findViewById(R.id.step_instruction_tv);
        instructionStep.setText(mStepInfo.getDescription());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(STEP_KEY, mStepInfo);
    }
}
