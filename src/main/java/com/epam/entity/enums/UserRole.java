package com.epam.entity.enums;

import com.epam.exception.UnknownEntityException;

import java.util.Locale;

public enum UserRole {
    ADMIN(1),
    USER(2);

    private final Integer id;

    UserRole(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
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
