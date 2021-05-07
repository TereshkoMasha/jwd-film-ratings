package com.epam.entity;


import com.epam.entity.enums.Genre;

import java.sql.Time;
import java.util.List;
import java.util.Objects;

public class Movie extends AbstractBaseEntity {
    private final String name;
    private final String tagline;
    private final Time duration;
    private final Genre genre;
    private final int releaseYear;
    private final Country releaseCountry;
    private final List<MovieCrewMember> movieCrew;

    private Movie(Builder builder) {
        this.name = builder.name;
        this.tagline = builder.tagline;
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

    public static class Builder {
        private String name;
        private String tagline;
        private Time duration;
        private Genre genre;
        private Integer releaseYear;
        private Country releaseCountry;
        private List<MovieCrewMember> movieCrew;

        private Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
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

        public Builder of(Movie movie) {
            this.name = movie.name;
            this.tagline = movie.tagline;
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
        return releaseYear == movie.releaseYear && name.equals(movie.name) && tagline.equals(movie.tagline) && duration.equals(movie.duration) && genre == movie.genre && releaseCountry.equals(movie.releaseCountry) && Objects.equals(movieCrew, movie.movieCrew);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, tagline, duration, genre, releaseYear, releaseCountry, movieCrew);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", tagline='" + tagline + '\'' +
                ", duration=" + duration +
                ", genre=" + genre +
                ", releaseYear=" + releaseYear +
                ", releaseCountry=" + releaseCountry +
                ", movieCrew=" + movieCrew +
                '}';
    }
}


