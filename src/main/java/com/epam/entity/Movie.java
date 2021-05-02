package com.epam.entity;


import com.epam.entity.enums.Genre;

import java.sql.Time;
import java.util.List;

public class Movie extends AbstractBaseEntity {
    private String name;
    private String tagline;
    private Time duration;
    private Genre genre;
    private int releaseYear;
    private List<MovieCrewMember> movieCrew;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MovieCrewMember> getMovieCrew() {
        return movieCrew;
    }

    public Genre getGenre() {
        return genre;
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

    public void setMovieCrew(List<MovieCrewMember> movieCrew) {
        this.movieCrew = movieCrew;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
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
}


