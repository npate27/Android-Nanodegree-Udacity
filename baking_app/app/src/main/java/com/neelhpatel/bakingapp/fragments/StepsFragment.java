package com.neelhpatel.bakingapp.fragments;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neelhpatel.bakingapp.R;
import com.neelhpatel.bakingapp.adapters.IngredientsAdapter;
import com.neelhpatel.bakingapp.adapters.StepListAdapter;
import com.neelhpatel.bakingapp.model.IngredientInfo;
import com.neelhpatel.bakingapp.model.StepInfo;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class StepsFragment extends Fragment implements StepListAdapter.StepsClickHandler {
    private static final String STEPS_KEY = "steps_key";
    private static final String INGREDIENTS_KEY = "ingredients_key";

    private ArrayList<StepInfo> mStepInfos;
    private ArrayList<IngredientInfo> mIngredientInfos;
    private OnStepClickListener mCallback;

    @BindView(R.id.steps_rv) RecyclerView mStepsRecyclerView;
    @BindView(R.id.ingredients_rv) RecyclerView mIngredientsRecyclerView;

    private int mPreviousIndex = -1;

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

    public static StepsFragment newInstance(ArrayList<StepInfo> stepInfos, ArrayList<IngredientInfo> mIngredientInfos){
        StepsFragment fragment = new StepsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STEPS_KEY, stepInfos);
        bundle.putParcelableArrayList(INGREDIENTS_KEY, mIngredientInfos);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_master_step_list, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Init vars based on arguments or savedInstanceState.
        if(savedInstanceState != null) {
            mStepInfos = savedInstanceState.getParcelableArrayList(STEPS_KEY);
            mIngredientInfos = savedInstanceState.getParcelableArrayList(INGREDIENTS_KEY);
        } else {
            mStepInfos = Objects.requireNonNull(getArguments()).getParcelableArrayList(STEPS_KEY);
            mIngredientInfos = getArguments().getParcelableArrayList(INGREDIENTS_KEY);
        }

        mStepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        StepListAdapter mStepsAdapter = new StepListAdapter(mStepInfos, this);
        mStepsRecyclerView.setAdapter(mStepsAdapter);
        DividerItemDecoration itemDecor = new DividerItemDecoration(Objects.requireNonNull(getContext()), VERTICAL);
        mStepsRecyclerView.addItemDecoration(itemDecor);

        mIngredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        IngredientsAdapter mIngredientsAdapter = new IngredientsAdapter(getContext(), mIngredientInfos);
        mIngredientsRecyclerView.setAdapter(mIngredientsAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(StepInfo stepInfo, int adapterPosition) {
        mCallback.onStepSelected(stepInfo);

        //This all is for highlighting the current step in the master-detail flow
        View selectedView = mStepsRecyclerView.getLayoutManager().findViewByPosition(adapterPosition);
        if (mPreviousIndex > -1) {
            View previousView = mStepsRecyclerView.getLayoutManager().findViewByPosition(mPreviousIndex);
            changeViewAttributes(previousView, R.color.greyAlpha, R.drawable.ic_arrow_forward_black_24dp);
            ((TextView) previousView.findViewById(R.id.step_item_tv)).setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.blackAlpha));

        }
        changeViewAttributes(selectedView, R.color.colorPrimary, R.drawable.ic_play_arrow_black_24dp);
        ((TextView) selectedView.findViewById(R.id.step_item_tv)).setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorAccent));

        mPreviousIndex = adapterPosition;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(STEPS_KEY, mStepInfos);
        outState.putParcelableArrayList(INGREDIENTS_KEY, mIngredientInfos);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void changeViewAttributes(View view, int backgroundColor, int iconDrawableId) {
        Context context = getContext();
        view.setBackgroundColor(ContextCompat.getColor(Objects.requireNonNull(context), backgroundColor));
        ((ImageView) view.findViewById(R.id.indicator_iv))
                .setImageDrawable(ContextCompat.getDrawable(context, iconDrawableId));
    }
}
