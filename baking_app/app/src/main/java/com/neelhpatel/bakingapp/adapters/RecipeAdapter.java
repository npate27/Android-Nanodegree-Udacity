package com.neelhpatel.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.neelhpatel.bakingapp.R;
import com.neelhpatel.bakingapp.model.RecipeInfo;
import com.neelhpatel.bakingapp.utils.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder>{

    private final Context mContext;
    private List<RecipeInfo> mRecipeInfos;
    private final RecipesClickHandler mRecipesClickHandler;

    public RecipeAdapter(Context context, List<RecipeInfo> recipeInfos, RecipesClickHandler clickHandler) {
        mContext = context;
        mRecipeInfos = recipeInfos;
        mRecipesClickHandler = clickHandler;
    }

    public interface RecipesClickHandler {
        void onClick(RecipeInfo recipeInfo);
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_card_list_item, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder viewHolder, int i) {
        RecipeInfo recipeInfo = mRecipeInfos.get(i);
        //TODO FIX THIS
        Glide.with(mContext)
                .load(R.drawable.loading_circle)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading_circle)
                        .error(R.drawable.stock_image_not_available))
                .into(viewHolder.recipeView);

        viewHolder.recipeTitle.setText(recipeInfo.getName());
        viewHolder.recipeServings.setText(mContext.getResources()
                .getString(R.string.servings_string, recipeInfo.getServings()));
    }

    @Override
    public int getItemCount() {
        return mRecipeInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView recipeView;
        public final TextView recipeTitle;
        public final TextView recipeServings;

        public ViewHolder(View itemView) {
            super(itemView);
            recipeView = itemView.findViewById(R.id.recipe_iv);
            recipeTitle = itemView.findViewById(R.id.recipe_name_tv);
            recipeServings = itemView.findViewById(R.id.recipe_servings_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mRecipesClickHandler.onClick(mRecipeInfos.get(adapterPosition));
        }
    }

    /**
     * Swaps the current list of RecipeInfo objects with a new one,
     * used for when the network request is completed so the
     * cards  can be displayed
     *
     * @param newRecipes List of RecipeInfo objects to be displayed by adapter
     */
    public void changeData(List<RecipeInfo> newRecipes) {
        mRecipeInfos = new ArrayList<>();
        mRecipeInfos.addAll(newRecipes);
        notifyDataSetChanged();

    }

}
