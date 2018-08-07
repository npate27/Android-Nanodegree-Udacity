package com.neelhpatel.bakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.neelhpatel.bakingapp.model.RecipeInfo;

public class PreferenceUtils {

    public static final String PREF_KEY = "pref_key";

    public static void saveRecipe(Context context, RecipeInfo recipeInfo) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipeInfo);
        editor.putString(PREF_KEY, json);
        editor.apply();

    }

    public static RecipeInfo getRecipe(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String json = sp.getString(PREF_KEY, "");
        Gson gson = new Gson();
        return gson.fromJson(json, RecipeInfo.class);
    }
}
