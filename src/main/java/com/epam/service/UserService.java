package com.epam.service;

import com.epam.entity.User;
import com.epam.entity.enums.UserStatus;
import com.epam.exception.DAOException;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface UserService extends BaseService<User> {

    Optional<User> registerUser(String login, String password, String name, String email) throws NoSuchAlgorithmException;

    boolean changePassword(User user, String newPassword);

    Optional<User> findByLogin(String login) ;

    boolean banUser(UserStatus status, int userId);

    boolean updateRating(Boolean action, int userId);

    int getUserRoleId(String login);

    boolean findUser(String login, String password);

}
