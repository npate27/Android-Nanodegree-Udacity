package com.neelhpatel.bakingapp;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.neelhpatel.bakingapp.adapters.RecipeAdapter;
import com.neelhpatel.bakingapp.model.RecipeInfo;
import com.neelhpatel.bakingapp.tasks.RecipeTask;
import com.neelhpatel.bakingapp.utils.NetworkUtils;
import com.neelhpatel.bakingapp.utils.SimpleIdlingResource;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipesClickHandler,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String RECIPE_KEY = "recipe_key";
    private static ArrayList<RecipeInfo> mRecipeInfos = new ArrayList<>();
    private static RecipeAdapter mRecipeAdapter;
    private static SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    private static SimpleIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mIdlingResource = new SimpleIdlingResource();
        mIdlingResource.setIdleState(false);
        fetchRecipes();

        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int cols = (dpWidth < 600 ) ? 1 : 2;
        RecyclerView recyclerView = findViewById(R.id.recipes_rv);
        recyclerView.setLayoutManager(new GridLayoutManager(this, cols));
        mRecipeAdapter = new RecipeAdapter(this, mRecipeInfos, this);
        recyclerView.setAdapter(mRecipeAdapter);
    }

    @Override
    public void onClick(RecipeInfo recipeInfo) {
        Intent intent = new Intent(this, IndividualRecipeActivity.class);
        intent.putExtra(RECIPE_KEY, recipeInfo);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void onDataRecieved(ArrayList<RecipeInfo> recipeInfos) {
        mRecipeInfos = recipeInfos;
        mRecipeAdapter.changeData(mRecipeInfos);
        mSwipeRefreshLayout.setRefreshing(false);
        Objects.requireNonNull(mIdlingResource).setIdleState(true);
    }

    /**
    * Starts AsyncTask which will fetch recipe data in the background.
    * If no internet, a toast message appears.
    */
    private void fetchRecipes() {
        if(NetworkUtils.isConnectedToInternet(this)) {
            new RecipeTask().execute(NetworkUtils.getMainUrl());
        } else {
            Toast.makeText(this, getResources().getString(R.string.toast_internet_string),
                    Toast.LENGTH_SHORT).show();
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * Triggered if user vertically swipes down.
     * Useful if user has internet/data turned off when first opening the app.
     *
     */
    @Override
    public void onRefresh() {
        fetchRecipes();
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
}
