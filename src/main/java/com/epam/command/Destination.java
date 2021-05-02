package com.epam.command;

public enum Destination {
    MAIN_PAGE("/jsp/main.jsp"),
    LOGIN("/jsp/login.jsp");

    private final String path;

    Destination(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
