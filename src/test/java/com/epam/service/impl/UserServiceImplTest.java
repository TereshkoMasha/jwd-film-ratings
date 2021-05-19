package com.epam.service.impl;

import com.epam.entity.User;
import com.epam.exception.DAOException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UserServiceImplTest {
    private final UserServiceImpl userService = new UserServiceImpl();

    @Test
    void changePassword() {
        User user = Mockito.mock(User.class);
        String password = "43573954";
        Mockito.when(user.getID()).thenReturn(6);
        Mockito.when(user.getPassword()).thenReturn(password);
        assertTrue(userService.changePassword(user, password));
    }

    @Test
    void testFindByLoginPositive() throws DAOException {
        assertEquals(userService.findByLogin("masha").get().getName(), "masha");
    }

    @Test
    void getUserRoleId() {
        assertEquals(2, userService.getUserRoleId("masha"));
    }

    @Test
    void findUser() {
        assertTrue(userService.findUser("masha", "123"));
    }


    @Test
    void deleteById() {
        int id = 7;
        assertTrue(userService.deleteById(id));
    }

    @Test
    void update() {
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