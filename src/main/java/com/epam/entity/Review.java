package com.epam.entity;

import com.epam.entity.enums.Appraisal;

import java.util.Objects;

public class Review extends AbstractBaseEntity {
    private final int userID;
    private final int movieID;
    private final String text;
    private final Appraisal rating;

    private Review(Builder builder) {
        this.userID = Objects.requireNonNull(builder.userID, "userID");
        this.movieID = Objects.requireNonNull(builder.movieID, "movieID");
        this.text = Objects.requireNonNull(builder.text);
        this.rating = Objects.requireNonNull(builder.rating);
    }

    public static Builder builder() {
        return new Builder();
    }

    public int getUserID() {
        return userID;
    }

    public int getMovieID() {
        return movieID;
    }

    public String getText() {
        return text;
    }

    public Appraisal getRating() {
        return rating;
    }


    public static class Builder {
        private Integer userID;
        private Integer movieID;
        private String text;
        private Appraisal rating;

        private Builder() {
        }

        public Builder setUserID(int userID) {
            this.userID = userID;
            return this;
        }

        public Builder setMovieID(int movieID) {
            this.movieID = movieID;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setRating(Appraisal rating) {
            this.rating = rating;
            return this;
        }

        public Builder of(Review review) {
            this.userID = review.userID;
            this.movieID = review.movieID;
            this.text = review.text;
            this.rating = review.rating;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }
}
