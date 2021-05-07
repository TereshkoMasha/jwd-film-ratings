package com.epam.service;

import com.epam.entity.User;
import com.epam.entity.enums.UserStatus;
import com.epam.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean registerUser(String login, String password, String name);

    boolean changePassword(User user, String newPassword);

    boolean findByLogin(String login) throws DAOException;

    boolean updateStatus(UserStatus status, int userId);

    int getUserRoleId(String login);

    boolean findUser(String login, String password);

    boolean update(User user);

    boolean deleteById(Integer id);

    Optional<User> getById(Integer id);

    List<User> findAll();
}
