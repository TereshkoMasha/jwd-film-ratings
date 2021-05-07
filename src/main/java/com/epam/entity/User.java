package com.epam.entity;

import com.epam.entity.enums.UserRole;
import com.epam.entity.enums.UserStatus;

import java.util.Objects;

public class User extends AbstractBaseEntity {

    private String login;

    private String password;

    private String name;

    private UserRole role;

    private UserStatus status;

    private Double rating;

    public User() {

    }

    private User(Builder builder) {
        this.login = Objects.requireNonNull(builder.login, "login");
        this.password = Objects.requireNonNull(builder.password, "password");
        this.name = Objects.requireNonNull(builder.name, "name");
        this.role = Objects.requireNonNull(builder.role, "role");
        this.status = Objects.requireNonNull(builder.status, "status");
        this.rating = Objects.requireNonNull(builder.rating, "rating");
    }

    public static Builder builder() {
        return new Builder();
    }


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

    public Double getRating() {
        return rating;
    }

    public static class Builder {
        private String login;
        private String password;
        private String name;
        private UserRole role;
        private UserStatus status;
        private Double rating;

        private Builder() {
        }

        public Builder setLogin(String login) {
            this.login = login;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setRole(UserRole role) {
            this.role = role;
            return this;
        }

        public Builder setStatus(UserStatus status) {
            this.status = status;
            return this;
        }

        public Builder setRating(Double rating) {
            this.rating = rating;
            return this;
        }

        public Builder of(User user) {
            this.login = user.login;
            this.password = user.password;
            this.name = user.name;
            this.role = user.role;
            this.status = user.status;
            this.rating = rating;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return login.equals(user.login) && password.equals(user.password) && name.equals(user.name) && role == user.role && status == user.status && rating.equals(user.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, name, role, status, rating);
    }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", rating=" + rating +
                '}';
    }
}

