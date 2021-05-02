package com.epam.service;

import com.epam.entity.User;
import com.epam.entity.enums.UserRole;
import com.epam.entity.enums.UserStatus;

import java.util.List;
import java.util.Optional;

public interface UserService {

    boolean registerUser(String login, String password, UserRole userRole, UserStatus userStatus);

    boolean changePassword(User user, String newPassword);

    boolean findByLogin(String login);

    //boolean updateRating(Appraisal appraisal, Integer userId);

    boolean updateStatus(UserStatus status, int userId);

    int getUserRoleId(String login);

    boolean findUser(String login, String password);

    void create(User user);

    boolean update(User user);

    void deleteById(Integer id);

    Optional<User> getById(Integer id);

    List<User> findAll();
}
