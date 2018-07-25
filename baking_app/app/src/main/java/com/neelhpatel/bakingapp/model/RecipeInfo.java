package com.neelhpatel.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RecipeInfo implements Parcelable {
   int id;
   String name;
   ArrayList<IngredientInfo> ingredients;
   ArrayList<StepInfo> steps;
   int servings;
   String image;

    public RecipeInfo(int id, String name, ArrayList<IngredientInfo> ingredients, ArrayList<StepInfo> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<IngredientInfo> getIngredients() {
        return ingredients;
    }

    public ArrayList<StepInfo> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public void appendSteps(StepInfo stepInfo) {
        steps.add(0, stepInfo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.ingredients);
        dest.writeTypedList(this.steps);
        dest.writeInt(this.servings);
        dest.writeString(this.image);
    }

    protected RecipeInfo(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.ingredients = in.createTypedArrayList(IngredientInfo.CREATOR);
        this.steps = in.createTypedArrayList(StepInfo.CREATOR);
        this.servings = in.readInt();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<RecipeInfo> CREATOR = new Parcelable.Creator<RecipeInfo>() {
        @Override
        public RecipeInfo createFromParcel(Parcel source) {
            return new RecipeInfo(source);
        }

        @Override
        public RecipeInfo[] newArray(int size) {
            return new RecipeInfo[size];
        }
    };
}
