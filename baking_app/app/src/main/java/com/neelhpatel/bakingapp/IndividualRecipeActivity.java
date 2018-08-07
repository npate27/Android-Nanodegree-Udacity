package com.neelhpatel.bakingapp;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.neelhpatel.bakingapp.fragments.StepFragment;
import com.neelhpatel.bakingapp.fragments.StepsFragment;
import com.neelhpatel.bakingapp.model.RecipeInfo;
import com.neelhpatel.bakingapp.model.StepInfo;
import com.neelhpatel.bakingapp.widget.RecipeIngredientsService;

import java.util.Objects;

public class IndividualRecipeActivity extends AppCompatActivity implements StepsFragment.OnStepClickListener {

    private boolean mTwoPane;
    private boolean isFirstBack;
    private RecipeInfo recipeInfo = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_recipe);

        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.RECIPE_KEY)) {
            recipeInfo = intent.getParcelableExtra(MainActivity.RECIPE_KEY);
            Objects.requireNonNull(getSupportActionBar()).setTitle(recipeInfo.getName());

            mTwoPane = (findViewById(R.id.step_container_container) != null);

            if (savedInstanceState == null) {
                Fragment fragment = StepsFragment.newInstance(recipeInfo.getSteps(), recipeInfo.getIngredients());
                performFragmentTransaction(fragment, R.id.step_fragment_container, true, true);
            }

        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.individual_recipe_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_to_widget_option:
                RecipeIngredientsService.updateWidget(this, recipeInfo);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStepSelected(StepInfo stepInfo) {
        int container_id = mTwoPane ? R.id.step_content_container : R.id.step_fragment_container;
        Fragment fragment = StepFragment.newInstance(stepInfo, recipeInfo.getSteps(), mTwoPane);
        performFragmentTransaction(fragment, container_id, false, false);
        if(!isFirstBack) {
            isFirstBack = true;
        }
    }

    @Override
    public void onBackPressed() {
        if(isFirstBack) {
            Fragment fragment = StepsFragment.newInstance(recipeInfo.getSteps(), recipeInfo.getIngredients());
            performFragmentTransaction(fragment, R.id.step_fragment_container, false, false);
            isFirstBack = false;
        } else {
            finish();
        }
    }

    private void performFragmentTransaction(Fragment fragment, int containerId, boolean addToBackStack, boolean isAdding){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if(isAdding) {
            ft.add(containerId, fragment);
        } else {
            ft.replace(containerId, fragment);
        }

        if(addToBackStack) {
            ft.addToBackStack(null);
        }
        ft.commit();
    }
}
