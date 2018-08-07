package com.neelhpatel.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.neelhpatel.bakingapp.IndividualRecipeActivity;
import com.neelhpatel.bakingapp.MainActivity;
import com.neelhpatel.bakingapp.R;
import com.neelhpatel.bakingapp.model.RecipeInfo;
import com.neelhpatel.bakingapp.utils.PreferenceUtils;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeIngredientsProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RecipeInfo recipeInfo = PreferenceUtils.getRecipe(context);
        if (recipeInfo != null) {
            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_ingredients_provider);

            Intent intent = new Intent(context, IndividualRecipeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable(MainActivity.RECIPE_KEY, recipeInfo);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            views.setTextViewText(R.id.widget_recipe_tv, recipeInfo.getName());
            views.setOnClickPendingIntent(R.id.widget_recipe_tv, pendingIntent);

            intent = new Intent(context, RecipeIngredientsService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            views.setRemoteAdapter(R.id.widget_ingredients_lv, intent);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_recipe_tv);
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    public static void updateAppWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

}

