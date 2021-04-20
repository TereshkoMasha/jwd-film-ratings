package com.epam.entity;

import com.epam.exception.UnknownEntityException;
import lombok.Getter;

import java.util.Locale;

public enum UserRole {
    ADMIN(1),
    USER(2);

    @Getter
    private final Integer id;

    UserRole(Integer id) {
        this.id = id;
    }

    public static UserRole resolveRoleById(Integer id) {
        UserRole[] values = values();
        for (UserRole userRole :
                values) {
            if (userRole.getId().equals(id)) return userRole;
        }
        throw new UnknownEntityException("Such id doesn't exist!");
    }


    @Override
    public String toString() {
        return name().toLowerCase(Locale.ROOT);
    }
}
