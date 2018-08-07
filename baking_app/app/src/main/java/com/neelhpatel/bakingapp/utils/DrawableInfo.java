package com.neelhpatel.bakingapp.utils;

import com.neelhpatel.bakingapp.R;

public class DrawableInfo {

    public static int getRecipeDrawableId(String name) {
        switch (name) {
            case "Nutella Pie":
                return R.drawable.nutella_pie;
            case "Brownies":
                return R.drawable.brownies;
            case "Yellow Cake":
                return R.drawable.yellow_cake;
            case "Cheesecake":
                return R.drawable.cheesecake;
            default:
                return R.drawable.stock_image_not_available;
        }
    }
}
