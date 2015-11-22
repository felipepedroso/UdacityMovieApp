package br.felipepedroso.udacitymovieapp;

/**
 * Created by FelipeAugusto on 22/11/2015.
 */
public class MovieInfo {
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
}
