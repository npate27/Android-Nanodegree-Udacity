package com.neelhpatel.bakingapp;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.neelhpatel.bakingapp.fragments.IngredientsFragment;
import com.neelhpatel.bakingapp.fragments.StepFragment;
import com.neelhpatel.bakingapp.fragments.StepsFragment;
import com.neelhpatel.bakingapp.model.RecipeInfo;
import com.neelhpatel.bakingapp.model.StepInfo;

public class IndividualRecipeActivity extends AppCompatActivity implements StepsFragment.OnStepClickListener {

    private boolean mTwoPane;
    private RecipeInfo recipeInfo = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_recipe);

        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.RECIPE_KEY)) {
            recipeInfo = intent.getParcelableExtra(MainActivity.RECIPE_KEY);

            mTwoPane = (findViewById(R.id.step_container_container) != null);

            if (savedInstanceState == null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment fragment = StepsFragment.newInstance(recipeInfo.getSteps());
                ft.add(R.id.step_fragment_container, fragment);
                ft.commit();
            }
        } else {
            finish();
        }
    }

    @Override
    public void onStepSelected(StepInfo stepInfo) {
        int container_id = mTwoPane ? R.id.step_content_container : R.id.step_fragment_container;

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (stepInfo == null) {
            Fragment fragment = IngredientsFragment.newInstance(recipeInfo.getIngredients());
            ft.replace(container_id, fragment);
        } else {
            Fragment fragment = StepFragment.newInstance(stepInfo);
            ft.replace(container_id, fragment);
        }
        ft.addToBackStack(null);
        ft.commit();
    }
}
