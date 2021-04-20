package com.epam.entity;

import com.epam.exception.UnknownEntityException;
import lombok.Getter;

import java.util.Locale;

public enum MovieRole {
    ACTOR(1),
    FILMMAKER(2),
    SCREENWRITER(3),
    STAGE_DIRECTOR(4);

    @Getter
    private final Integer id;

    MovieRole(Integer id) {
        this.id = id;
    }

    public static MovieRole resolveRoleById(Integer id) {
        MovieRole[] values = values();
        for (MovieRole role :
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
