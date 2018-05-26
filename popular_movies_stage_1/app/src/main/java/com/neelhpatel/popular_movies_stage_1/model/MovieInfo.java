package com.neelhpatel.popular_movies_stage_1.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieInfo implements Parcelable {
    final int id;
    final String posterPath;
    final String title;
    final String releaseDate;
    final double voteAverage;
    final String overview;

    public MovieInfo(int id, String posterPath, String title, String releaseDate,
                     double voteAverage, String overview) {
        this.id = id;
        this.posterPath = posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }

    protected MovieInfo(Parcel in) {
        id = in.readInt();
        posterPath = in.readString();
        title = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readDouble();
        overview = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getReleaseDateFormatted() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
        Date releaseDate;
        try {
            releaseDate = dateFormat.parse(getReleaseDate());
            SimpleDateFormat releasedDateFormatted = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            return releasedDateFormatted.format(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(posterPath);
        dest.writeString(title);
        dest.writeString(releaseDate);
        dest.writeDouble(voteAverage);
        dest.writeString(overview);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<MovieInfo> CREATOR = new Parcelable.Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel in) {
            return new MovieInfo(in);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };
}
