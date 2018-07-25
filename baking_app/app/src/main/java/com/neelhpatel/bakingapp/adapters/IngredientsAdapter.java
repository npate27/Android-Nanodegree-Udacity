package com.neelhpatel.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.neelhpatel.bakingapp.R;
import com.neelhpatel.bakingapp.model.IngredientInfo;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private final Context mContext;
    private List<IngredientInfo> mIngredientInfos;

    public IngredientsAdapter(Context context, List<IngredientInfo> ingredientInfos) {
        mContext = context;
        mIngredientInfos = ingredientInfos;
    }

    @NonNull
    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ingredient_list_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder viewHolder, int i) {
        IngredientInfo ingredientInfo = mIngredientInfos.get(i);
        viewHolder.ingredientNameTv.setText(ingredientInfo.getIngredient());
        viewHolder.quantityTv.setText(mContext.getResources()
                .getString(R.string.ingredients_string, ingredientInfo.getQuantity(), ingredientInfo.getMeasure()));
    }

    @Override
    public int getItemCount() {
        return mIngredientInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView ingredientNameTv;
        public TextView quantityTv;

        public ViewHolder(View itemView) {
            super(itemView);
            ingredientNameTv = itemView.findViewById(R.id.ingredient_name_tv);
            quantityTv = itemView.findViewById(R.id.quantity_tv);
        }
    }
}