package br.felipepedroso.udacitymovieapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by FelipeAugusto on 22/11/2015.
 */
public class MovieInfo implements Parcelable{
    private final String title;
    private final String synopsis;
    private final String posterPath;
    private final double popularity;
    private final String releaseDate;
    private final double userRating;
    private final String id;

    public MovieInfo(String id, String title, String synopsis, String posterPath, double popularity, double userRating, String releaseDate) {
        this.id = id;
        this.title = title;
        this.synopsis = synopsis;
        this.posterPath = posterPath;
        this.popularity = popularity;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return  String.format("%s - ID: %s - Popularity: %f - User Rating: %f - Release Date: %s", title, id, popularity, userRating, releaseDate);
    }

    public String getTitle() {
        return title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getUserRating() {
        return userRating;
    }

    public String getId() {
        return id;
    }

    protected MovieInfo(Parcel in) {
        title = in.readString();
        synopsis = in.readString();
        posterPath = in.readString();
        popularity = in.readDouble();
        releaseDate = in.readString();
        userRating = in.readDouble();
        id = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(synopsis);
        dest.writeString(posterPath);
        dest.writeDouble(popularity);
        dest.writeString(releaseDate);
        dest.writeDouble(userRating);
        dest.writeString(id);
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
