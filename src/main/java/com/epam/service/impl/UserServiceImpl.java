package com.epam.service.impl;

import com.epam.dao.impl.UserDaoImpl;
import com.epam.entity.User;
import com.epam.entity.enums.UserRole;
import com.epam.entity.enums.UserStatus;
import com.epam.exception.DAOException;
import com.epam.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    private static final UserDaoImpl userDao = UserDaoImpl.INSTANCE;

    @Override
    public Optional<User> registerUser(String login, String password, String name) {
        String md5Hex = DigestUtils
                .md5Hex(password).toUpperCase();
        if (!findUser(login, password)) {
            User user = User.builder()
                    .setName(name)
                    .setLogin(login)
                    .setPassword(md5Hex)
                    .setRole(UserRole.USER)
                    .setStatus(UserStatus.LOW)
                    .setRating(1.0).build();
            create(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public boolean changePassword(User user, String newPassword) {
        User.builder().of(user).setPassword(DigestUtils
                .md5Hex(newPassword).toUpperCase());
        try {
            return userDao.update(user);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    public Optional<User> findByLogin(String login) {
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
            return userDao.findUserByLoginAndPassword(login, DigestUtils
                    .md5Hex(password).toUpperCase());
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
    public boolean banUser(UserStatus status, Integer userId) {
        try {
            return userDao.updateUserStatus(status, userId);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return false;
    }


    @Override
    public boolean updateRatingAfterEvaluating(Integer userId, Boolean action) {
        try {
            return userDao.updateUserRatingAfterEvaluating(action, userId);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    public void updateUserRating(Integer id, Double rating) {
        try {
            userDao.updateUserRating(id, rating);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
    }
}
