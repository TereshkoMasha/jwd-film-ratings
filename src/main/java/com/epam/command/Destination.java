package com.epam.command;

public enum Destination {
    MAIN_PAGE("/jsp/main.jsp"),
    LOGIN("/jsp/login.jsp"),
    ERROR("/jsp/error.jsp"),
    MOVIE_PAGE("/jsp/movie.jsp"),
    USERS("/jsp/admin/users.jsp"),
    MAIN_REDIRECT("/controller?command=main");

    private final String path;

    Destination(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
