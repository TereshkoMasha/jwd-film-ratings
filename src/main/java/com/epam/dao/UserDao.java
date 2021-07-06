package com.epam.dao;

import com.epam.entity.User;
import com.epam.entity.enums.UserStatus;
import com.epam.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> findAllByRoleID(Integer id) throws DAOException;

    boolean checkLogin(String login) throws DAOException;

    Optional<User> findByLogin(String login) throws DAOException;

    boolean findByLoginPassword(String login, String password) throws DAOException;

    boolean updateStatus(UserStatus userStatus, Integer id) throws DAOException;

    boolean updateRatingAfterEvaluating(Boolean action, Integer id) throws DAOException;

    boolean updateRating(Integer id, Double rating) throws DAOException;

    Integer getRoleId(String login) throws DAOException;

    List<User> findAllByMovieId(Integer id) throws DAOException;

}

