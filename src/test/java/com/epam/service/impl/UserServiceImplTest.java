package com.epam.service.impl;

import com.epam.entity.User;
import com.epam.entity.enums.UserStatus;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;


class UserServiceImplTest {
    private final UserServiceImpl userService = new UserServiceImpl();

    @Test
    void changePassword() throws ServiceException {
        User user = Mockito.mock(User.class);
        String password = DigestUtils
                .md5Hex("43573954").toUpperCase();
        Mockito.when(user.getId()).thenReturn(6);
        Mockito.when(user.getPassword()).thenReturn(password);
        assertTrue(userService.changePassword(user.getId(), password));
    }

    @Test
    void testFindByLoginPositive() throws DAOException {
        Assertions.assertEquals(userService.findByLogin("user").get().getName(), "user");
    }

    @Test
    void getUserRoleId() throws ServiceException {
        Assertions.assertEquals(2, userService.getRoleId("user"));
    }

    @Test
    void findUser() throws ServiceException {
        assertTrue(userService.checkLogin("user"));
    }

    @Test
    void deleteById() throws ServiceException {
        int id = 7;
        assertTrue(userService.deleteById(id));
    }

    @Test
    void getById() throws ServiceException {
        User user = Mockito.mock(User.class);
        Mockito.when(user.getId()).thenReturn(1);
        Assertions.assertEquals(userService.getById(1).get().getId(), user.getId());
    }

    @Test
    void registerUser() throws ServiceException {
        userService.registerUser("max112", "4357395400", "Max");
        assertTrue(userService.checkLogin("max112"));
    }

    @Test
    void updateUserStatus() throws ServiceException {
        User user = Mockito.mock(User.class);
        Mockito.when(user.getStatus()).thenReturn(UserStatus.BANNED);
        if (userService.getById(2).get().getStatus() != UserStatus.BANNED) {
            userService.blockUser(UserStatus.BANNED, 2);
        }
        Assertions.assertEquals(userService.getById(2).get().getStatus(), user.getStatus());
    }

    @Test
    void updateUserRating() throws ServiceException {
        assertTrue(userService.updateRatingAfterEvaluating(2, true));
    }

    @Test
    void adminUpdateUserRating() throws ServiceException {
        userService.updateRating(2, 5.0);
        Assertions.assertEquals(5, (double) userService.getById(2).get().getRating());
    }

    @Test
    void getLastInsertId() throws ServiceException {
        List<User> userList = userService.findAll();
        User user = userList.get(userList.size() - 1);
        assertTrue(userService.registerUser("max112", "4357395400", "Max") > user.getId());
    }

    @Test
    void findAllUsersByMovieId() {

    }
}