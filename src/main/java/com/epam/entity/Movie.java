package com.epam.entity;


import java.sql.Time;
import java.util.ArrayList;

public class Movie extends AbstractBaseEntity {
    private String title;
    private Double rating;
    private ArrayList<MovieAbstractEntity> movieCast;
    private Genre genre;
    private Country releaseCountry;
    private int releaseYear;
    private Time duration;
    private String tagline;

    public Movie() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setMovieCast(ArrayList<MovieAbstractEntity> movieCast) {
        this.movieCast = movieCast;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setReleaseCountry(Country releaseCountry) {
        this.releaseCountry = releaseCountry;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public Double getRating() {
        return rating;
    }

    public ArrayList<MovieAbstractEntity> getMovieCast() {
        return movieCast;
    }

    public Genre getGenre() {
        return genre;
    }

    public Country getReleaseCountry() {
        return releaseCountry;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public Time getDuration() {
        return duration;
    }

    public String getTagline() {
        return tagline;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", rating=" + rating +
                ", movieCast=" + movieCast +
                ", genre=" + genre +
                ", releaseCountry=" + releaseCountry +
                ", releaseYear=" + releaseYear +
                ", duration=" + duration +
                ", tagline='" + tagline + '\'' +
                '}';
    }
}


