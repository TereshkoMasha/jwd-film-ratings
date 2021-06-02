package com.epam.service.impl;

import com.epam.entity.User;
import com.epam.exception.DAOException;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UserServiceImplTest {
    private final UserServiceImpl userService = new UserServiceImpl();

    @Test
    void changePassword() {
        User user = Mockito.mock(User.class);
        String password = DigestUtils
                .md5Hex("43573954").toUpperCase();
        Mockito.when(user.getId()).thenReturn(6);
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
    void getById() {
        User user = Mockito.mock(User.class);
        Mockito.when(user.getId()).thenReturn(1);
        assertEquals(userService.getById(1).get().getId(), user.getId());
    }

    @Test
    void registerUser() {
        userService.registerUser("max112", "4357395400", "Max", "max22@gmail.com");
        assertTrue(userService.findUser("max112", "4357395400"));
    }
}