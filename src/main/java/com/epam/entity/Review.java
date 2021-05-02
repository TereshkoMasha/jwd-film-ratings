package com.epam.entity;

import com.epam.entity.enums.Appraisal;

public class Review {
    private int userID;
    private int movieID;
    private String text;
    private Appraisal rating;

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

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRating(Appraisal rating) {
        this.rating = rating;
    }
}
