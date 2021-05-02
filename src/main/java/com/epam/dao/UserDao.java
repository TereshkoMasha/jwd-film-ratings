package com.epam.dao;

import com.epam.entity.enums.Appraisal;
import com.epam.entity.User;
import com.epam.entity.enums.UserStatus;
import com.epam.exception.DAOException;

import java.util.List;

public interface UserDao {

    List<User> findAllByRoleID(Integer id) throws DAOException;

    boolean findUserByLoginAndPassword(String login, String password) throws DAOException;

    boolean findUserByLogin(String login) throws DAOException;

    boolean updateUserStatus(UserStatus userStatus, Integer id) throws DAOException;

    //boolean updateUserRating(Appraisal rating, Integer id) throws DAOException;

    Integer getUserRoleId(String login) throws DAOException;

}
