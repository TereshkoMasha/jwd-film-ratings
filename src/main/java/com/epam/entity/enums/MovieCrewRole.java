package com.epam.entity.enums;

import com.epam.exception.UnknownEntityException;

import java.util.Locale;

public enum MovieCrewRole {
    FILMMAKER(1),
    ACTOR(2),
    SCREENWRITER(3),
    STAGE_DIRECTOR(4);

    private final Integer id;

    MovieCrewRole(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }


    public static MovieCrewRole resolveRoleById(Integer id) {
        MovieCrewRole[] values = values();
        for (MovieCrewRole role :
                values) {
            if (role.getId().equals(id)) return role;
        }
        throw new UnknownEntityException("Such id doesn't exist!");
    }

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ROOT);
    }


}
