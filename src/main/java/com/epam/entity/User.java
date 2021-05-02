package com.epam.entity;

import com.epam.entity.enums.UserRole;
import com.epam.entity.enums.UserStatus;

public class User extends AbstractBaseEntity {
    private String login;
    private String password;
    private String name;
    private UserRole role;
    private UserStatus status;
    private Double userRating;

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public UserRole getRole() {
        return role;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }
}
