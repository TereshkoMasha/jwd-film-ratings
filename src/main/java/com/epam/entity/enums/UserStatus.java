package com.epam.entity.enums;

import com.epam.exception.UnknownEntityException;

import java.util.Locale;

public enum UserStatus {
    BANNED(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    GOD(4);

    private final Integer id;

    UserStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static UserStatus resolveRoleById(Integer id) {
        UserStatus[] values = values();
        for (UserStatus userStatus :
                values) {
            if (userStatus.getId().equals(id)) return userStatus;
        }
        throw new UnknownEntityException("Such id doesn't exist!");
    }

    @Override
    public String toString() {
        return name().toLowerCase(Locale.ROOT);
    }

}
