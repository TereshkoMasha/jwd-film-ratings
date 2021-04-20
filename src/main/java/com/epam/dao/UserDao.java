package com.epam.dao;

import com.epam.entity.User;
import com.epam.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    List<User> findAllByRoleID(Integer id) throws DAOException;

    int getUserRoleId(String login) throws DAOException;

    Optional<User> findByLogin(String login) throws DAOException;

}
