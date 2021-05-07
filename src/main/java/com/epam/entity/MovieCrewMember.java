package com.epam.entity;


import com.epam.entity.enums.MovieCrewRole;

import java.util.Objects;

public class MovieCrewMember extends AbstractBaseEntity {
    private final String name;
    private final MovieCrewRole role;

    private MovieCrewMember(Builder builder) {
        this.name = builder.name;
        this.role = builder.role;
    }

    public static Builder builder() {
        return new Builder();
    }


    public String getName() {
        return name;
    }

    public MovieCrewRole getRole() {
        return role;
    }

    public static class Builder {
        private String name;
        private MovieCrewRole role;

        private Builder() {
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setRole(MovieCrewRole role) {
            this.role = role;
            return this;
        }

        public Builder of(MovieCrewMember movieCrewMember) {
            this.name = movieCrewMember.name;
            this.role = movieCrewMember.role;
            return this;
        }

        public MovieCrewMember build() {
            return new MovieCrewMember(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieCrewMember that = (MovieCrewMember) o;
        return name.equals(that.name) && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, role);
    }

    @Override
    public String toString() {
        return "MovieCrewMember{" +
                "name='" + name + '\'' +
                ", role=" + role +
                '}';
    }
}
