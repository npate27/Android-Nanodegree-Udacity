package com.neelhpatel.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.neelhpatel.bakingapp.R;
import com.neelhpatel.bakingapp.adapters.IngredientsAdapter;
import com.neelhpatel.bakingapp.model.IngredientInfo;

import java.util.ArrayList;

public class IngredientsFragment extends Fragment {
    private static final String INGREDIENTS_KEY = "ingredients_key";
    private ArrayList<IngredientInfo> mIngredientInfos;
    RecyclerView recyclerView;

    public IngredientsFragment() {}

    public static IngredientsFragment newInstance(ArrayList<IngredientInfo> ingredientInfos){
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(INGREDIENTS_KEY, ingredientInfos);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_step_ingredients, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(savedInstanceState != null) {
            mIngredientInfos = savedInstanceState.getParcelableArrayList(INGREDIENTS_KEY);
        } else {
            mIngredientInfos = getArguments().getParcelableArrayList(INGREDIENTS_KEY);
        }
        recyclerView = view.findViewById(R.id.ingredients_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        IngredientsAdapter mAdapter = new IngredientsAdapter(getContext(), mIngredientInfos);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(INGREDIENTS_KEY, mIngredientInfos);
    }
}
