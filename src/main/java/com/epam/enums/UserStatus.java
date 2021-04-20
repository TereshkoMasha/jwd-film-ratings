package com.epam.entity;

import com.epam.exception.UnknownEntityException;
import lombok.Getter;

import java.util.Locale;

public enum UserStatus {
    VERIFIED(1),
    BANNED(2);
    @Getter
    private final Integer id;

    UserStatus(Integer id) {
        this.id = id;
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
