package com.epam.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserServiceImplTest {
    private final UserServiceImpl userService = new UserServiceImpl();

    @Test
    void registerUser() {
    }

    @Test
    void changePassword() {
    }

    @Test
    void testFindByLoginPositive() {
        assertTrue(userService.findByLogin("masha2"));
    }

    @Test
    void testFindByLoginNegative() {
        assertFalse(userService.findByLogin("jacson1991"));
    }

    @Test
    void getUserRoleId() {
    }

    @Test
    void findUser() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void getById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void updateStatus() {
    }

}