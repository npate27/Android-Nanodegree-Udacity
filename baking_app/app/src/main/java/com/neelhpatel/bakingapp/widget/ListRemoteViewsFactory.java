package com.neelhpatel.bakingapp.widget;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.neelhpatel.bakingapp.R;
import com.neelhpatel.bakingapp.model.IngredientInfo;
import com.neelhpatel.bakingapp.model.RecipeInfo;
import com.neelhpatel.bakingapp.utils.PreferenceUtils;

public class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private RecipeInfo recipeInfo;

    public ListRemoteViewsFactory(Context context) {
        mContext = context;
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        recipeInfo = PreferenceUtils.getRecipe(mContext);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipeInfo.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        IngredientInfo ingredientInfo = recipeInfo.getIngredients().get(position);
        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredient_list_item);
        row.setTextViewText(R.id.ingredient_name_tv,
                mContext.getResources().getString(R.string.ingredients_name_string, ingredientInfo.getIngredient()));
        row.setTextViewText(R.id.quantity_tv,
                mContext.getResources().getString(R.string.ingredient_amount_string,
                        ingredientInfo.getQuantity(), ingredientInfo.getMeasure()));
        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
