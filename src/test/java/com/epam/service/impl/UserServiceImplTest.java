package com.epam.service.impl;

import com.epam.exception.DAOException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDaoImplTest {


    @Test
    void getById() throws DAOException {
        UserServiceImpl userService = new UserServiceImpl();
        assertEquals("Masha", userService.getById(2).get().getName());
    }
}