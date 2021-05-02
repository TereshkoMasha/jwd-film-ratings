package com.epam.entity;


import com.epam.entity.enums.MovieCrewRole;

public class MovieCrewMember extends AbstractBaseEntity {
    private String name;
    private MovieCrewRole role;

    public String getName() {
        return name;
    }

    public MovieCrewRole getRole() {
        return role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(MovieCrewRole role) {
        this.role = role;
    }
}
