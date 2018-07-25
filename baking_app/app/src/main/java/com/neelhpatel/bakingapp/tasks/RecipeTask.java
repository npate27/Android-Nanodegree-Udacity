package com.neelhpatel.bakingapp.tasks;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neelhpatel.bakingapp.MainActivity;
import com.neelhpatel.bakingapp.model.RecipeInfo;
import com.neelhpatel.bakingapp.utils.NetworkUtils;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;

public class RecipeTask extends AsyncTask<URL, Void, String> {

    @Override
    protected String doInBackground(URL... urls) {
        if(android.os.Debug.isDebuggerConnected())
            android.os.Debug.waitForDebugger();

        URL url = urls[0];
        String recipeJson = null;
        try {
            recipeJson = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipeJson;
    }

    @Override
    protected void onPostExecute(String recipeJson) {
        super.onPostExecute(recipeJson);
        Type foundListType = new TypeToken<ArrayList<RecipeInfo>>(){}.getType();
        Gson gson = new Gson();
        ArrayList<RecipeInfo> recipeInfos = gson.fromJson(recipeJson, foundListType);
        MainActivity.onDataRecieved(recipeInfos);
    }
}