package com.epam.entity;


import com.epam.entity.enums.Genre;

import java.sql.Time;
import java.util.List;
import java.util.Objects;

public class Movie extends AbstractBaseEntity {
    private final String name;
    private final String tagline;
    private final String description;
    private final Time duration;
    private final Genre genre;
    private final String poster;
    private final int releaseYear;
    private final Country releaseCountry;
    private final List<MovieCrewMember> movieCrew;

    private Movie(Builder builder) {
        this.name = builder.name;
        this.poster = builder.poster;
        this.tagline = builder.tagline;
        this.description = builder.description;
        this.duration = builder.duration;
        this.genre = builder.genre;
        this.releaseYear = Objects.requireNonNull(builder.releaseYear, "releaseYear");
        this.releaseCountry = builder.releaseCountry;
        this.movieCrew = builder.movieCrew;
    }

    public static Builder builder() {
        return new Builder();
    }


    public String getName() {
        return name;
    }

    public String getTagline() {
        return tagline;
    }

    public Time getDuration() {
        return duration;
    }

    public Genre getGenre() {
        return genre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public Country getReleaseCountry() {
        return releaseCountry;
    }

    public List<MovieCrewMember> getMovieCrew() {
        return movieCrew;
    }

    public String getPoster() {
        return poster;
    }

    public String getDescription() {
        return description;
    }

    public static class Builder {
        private String name;
        private String tagline;
        private String description;
        private Time duration;
        private Genre genre;
        private String poster;
        private Integer releaseYear;
        private Country releaseCountry;
        private List<MovieCrewMember> movieCrew;

        private Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setPoser(String poster) {
            this.poster = poster;
            return this;
        }

        public Builder setTagline(String tagline) {
            this.tagline = tagline;
            return this;
        }

        public Builder setDuration(Time duration) {
            this.duration = duration;
            return this;
        }

        public Builder setGenre(Genre genre) {
            this.genre = genre;
            return this;
        }

        public Builder setReleaseYear(int releaseYear) {
            this.releaseYear = releaseYear;
            return this;
        }

        public Builder setReleaseCountry(Country releaseCountry) {
            this.releaseCountry = releaseCountry;
            return this;
        }

        public Builder setMovieCrew(List<MovieCrewMember> movieCrew) {
            this.movieCrew = movieCrew;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder of(Movie movie) {
            this.name = movie.name;
            this.poster = movie.poster;
            this.tagline = movie.tagline;
            this.description = movie.description;
            this.duration = movie.duration;
            this.genre = movie.genre;
            this.releaseYear = movie.releaseYear;
            this.releaseCountry = movie.releaseCountry;
            this.movieCrew = movie.movieCrew;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return releaseYear == movie.releaseYear && Objects.equals(name, movie.name) && Objects.equals(tagline, movie.tagline) && Objects.equals(description, movie.description) && Objects.equals(duration, movie.duration) && genre == movie.genre && Objects.equals(poster, movie.poster) && Objects.equals(releaseCountry, movie.releaseCountry) && Objects.equals(movieCrew, movie.movieCrew);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tagline, description, duration, genre, poster, releaseYear, releaseCountry, movieCrew);
    }
}


