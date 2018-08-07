package com.neelhpatel.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class IngredientInfo implements Parcelable {
   private double quantity;
   private String measure;
   private String ingredient;

    public IngredientInfo(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient.substring(0, 1).toUpperCase() + ingredient.substring(1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.quantity);
        dest.writeString(this.measure);
        dest.writeString(this.ingredient);
    }

    private IngredientInfo(Parcel in) {
        this.quantity = in.readDouble();
        this.measure = in.readString();
        this.ingredient = in.readString();
    }

    public static final Parcelable.Creator<IngredientInfo> CREATOR = new Parcelable.Creator<IngredientInfo>() {
        @Override
        public IngredientInfo createFromParcel(Parcel source) {
            return new IngredientInfo(source);
        }

        @Override
        public IngredientInfo[] newArray(int size) {
            return new IngredientInfo[size];
        }
    };
}
