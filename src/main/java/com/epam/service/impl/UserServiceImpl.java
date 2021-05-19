package com.epam.service.impl;

import com.epam.dao.impl.UserDaoImpl;
import com.epam.entity.User;
import com.epam.entity.enums.UserRole;
import com.epam.entity.enums.UserStatus;
import com.epam.exception.DAOException;
import com.epam.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
    private static final UserDaoImpl userDao = UserDaoImpl.INSTANCE;

    @Override
    public boolean registerUser(String login, String password, String name) {
        User user = User.builder()
                .setName(name)
                .setLogin(login)
                .setPassword(password)
                .setRole(UserRole.USER)
                .setStatus(UserStatus.LOW)
                .setRating(5.0).build();
        create(user);
        return true;
    }

    @Override
    public boolean changePassword(User user, String newPassword) {
        User.builder().of(user).setPassword(newPassword);
        try {
            return userDao.update(user);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    public Optional<User> findByLogin(String login) throws DAOException {
        return userDao.findUserByLogin(login);
    }

    @Override
    public int getUserRoleId(String login) {
        int id = -1;
        try {
            id = userDao.getUserRoleId(login);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return id;
    }

    @Override
    public boolean findUser(String login, String password) {
        try {
            return userDao.findUserByLoginAndPassword(login, password);
        } catch (DAOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(User user) {
        try {
            return userDao.update(user);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        return userDao.deleteById(id);
    }

    @Override
    public Optional<User> getById(Integer id) {
        try {
            Optional<User> optionalUser = userDao.getById(id);
            if (optionalUser.isPresent()) {
                return optionalUser;
            }
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> userList = null;
        try {
            userList = userDao.findAll();
            if (!userList.isEmpty()) {
                return userList;
            }
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return userList;
    }

    private void create(User user) {
        try {
            userDao.create(user);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public boolean updateStatus(UserStatus status, int userId) {
        try {
            return userDao.updateUserStatus(status, userId);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return false;
    }
}
