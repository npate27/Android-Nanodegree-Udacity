package com.neelhpatel.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

import com.neelhpatel.bakingapp.model.RecipeInfo;
import com.neelhpatel.bakingapp.utils.PreferenceUtils;

public class RecipeIngredientsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        return new ListRemoteViewsFactory(getApplicationContext());
    }

    public static void updateWidget(Context context, RecipeInfo recipeInfo) {
        PreferenceUtils.saveRecipe(context, recipeInfo);
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, RecipeIngredientsProvider.class));
        RecipeIngredientsProvider.updateAppWidgets(context, appWidgetManager, appWidgetIds);
    }
}
