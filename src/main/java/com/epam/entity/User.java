package com.epam.entity;

public class User extends AbstractBaseEntity {


    private String password;
    private String login;
    private UserRole role;
    private UserStatus status;
    private Double rating;

    public User() {
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    public UserRole getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Double getRating() {
        return rating;
    }
}
