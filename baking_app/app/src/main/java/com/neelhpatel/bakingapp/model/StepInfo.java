package com.neelhpatel.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class StepInfo implements Parcelable {
   private int id;
   private String shortDescription;
   private String description;
   private String videoURL;
   private String thumbnailURL;

    public StepInfo(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoURL);
        dest.writeString(this.thumbnailURL);
    }

    private StepInfo(Parcel in) {
        this.id = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoURL = in.readString();
        this.thumbnailURL = in.readString();
    }

    public static final Parcelable.Creator<StepInfo> CREATOR = new Parcelable.Creator<StepInfo>() {
        @Override
        public StepInfo createFromParcel(Parcel source) {
            return new StepInfo(source);
        }

        @Override
        public StepInfo[] newArray(int size) {
            return new StepInfo[size];
        }
    };
}
