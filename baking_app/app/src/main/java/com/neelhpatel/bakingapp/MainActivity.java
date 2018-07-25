package com.neelhpatel.bakingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.neelhpatel.bakingapp.adapters.RecipeAdapter;
import com.neelhpatel.bakingapp.model.RecipeInfo;
import com.neelhpatel.bakingapp.model.StepInfo;
import com.neelhpatel.bakingapp.tasks.RecipeTask;
import com.neelhpatel.bakingapp.utils.NetworkUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipesClickHandler {

    public static final String RECIPE_KEY = "recipe_key";
    private static ArrayList<RecipeInfo> mRecipeInfos = new ArrayList<>();
    private static RecipeAdapter mRecipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RecipeTask().execute(NetworkUtils.getMainUrl());
        RecyclerView recyclerView = findViewById(R.id.recipes_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        mRecipeAdapter = new RecipeAdapter(this, mRecipeInfos, this);
        recyclerView.setAdapter(mRecipeAdapter);
    }

    @Override
    public void onClick(RecipeInfo recipeInfo) {
        Intent intent = new Intent(this, IndividualRecipeActivity.class);
        recipeInfo.appendSteps(new StepInfo(-1, "Recipe Ingredients", null, null, null));
        intent.putExtra(RECIPE_KEY, recipeInfo);
        startActivity(intent);
    }

    public static void onDataRecieved(ArrayList<RecipeInfo> recipeInfos) {
        mRecipeInfos = recipeInfos;
        mRecipeAdapter.changeData(mRecipeInfos);
    }
}
