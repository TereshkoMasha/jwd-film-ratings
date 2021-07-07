package com.epam.service.impl;

import com.epam.dao.impl.UserDaoImpl;
import com.epam.entity.User;
import com.epam.entity.enums.UserRole;
import com.epam.entity.enums.UserStatus;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
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
    public int registerUser(String login, String password, String name) {
        try {
            String md5Hex = DigestUtils
                    .md5Hex(password).toUpperCase();
            if (!checkLogin(login)) {
                User user = User.builder()
                        .setName(name)
                        .setLogin(login)
                        .setPassword(md5Hex)
                        .setRole(UserRole.USER)
                        .setStatus(UserStatus.LOW)
                        .setRating(1.0).build();
                return create(user);
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
        }
        return 0;
    }

    @Override
    public boolean changePassword(Integer id, String newPassword) throws ServiceException {
        Optional<User> optionalUser = getById(id);
        try {
            if (optionalUser.isPresent()) {
                User user = User.builder().of(optionalUser.get()).setPassword(DigestUtils
                        .md5Hex(newPassword).toUpperCase()).build();
                return userDao.update(user);
            }
            return false;
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userDao.findByLogin(login);
    }


    @Override
    public int getRoleId(String login) throws ServiceException {
        int id;
        try {
            id = userDao.getRoleId(login);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
        return id;
    }

    public boolean checkLogin(String login) throws ServiceException {
        try {
            return userDao.checkLogin(login);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public boolean findByLoginPassword(String login, String password) throws ServiceException {
        try {
            return userDao.findByLoginPassword(login, DigestUtils.md5Hex(password).toUpperCase());
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public List<User> findAllUsersByMovieId(Integer id) throws ServiceException {
        List<User> userList = null;
        try {
            userList = userDao.findAllByMovieId(id);
            if (!userList.isEmpty()) {
                return userList;
            }
        } catch (DAOException e) {
            LOGGER.error("Service Execution Exception", e);
            throw new ServiceException();
        }
        return userList;
    }

    @Override
    public boolean update(User user) throws ServiceException {
        try {
            return userDao.update(user);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public boolean deleteById(Integer id) throws ServiceException {
        try {
            return userDao.deleteById(id);
        } catch (DAOException e) {
            LOGGER.error("Service Execution Exception", e);
            throw new ServiceException();
        }
    }

    @Override
    public Optional<User> getById(Integer id) throws ServiceException {
        try {
            Optional<User> optionalUser = userDao.getById(id);
            if (optionalUser.isPresent()) {
                return optionalUser;
            }
        } catch (DAOException e) {
            LOGGER.error("Service Execution Exception", e);
            throw new ServiceException();
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAll() throws ServiceException {
        List<User> userList = null;
        try {
            userList = userDao.findAll();
            if (!userList.isEmpty()) {
                return userList;
            }
        } catch (DAOException e) {
            LOGGER.error("Service Execution Exception", e);
            throw new ServiceException();
        }
        return userList;
    }


    @Override
    public boolean updateStatus(UserStatus status, Integer userId) throws ServiceException {
        try {
            return userDao.updateStatus(status, userId);
        } catch (DAOException e) {
            LOGGER.error("Exception during data update", e);
            throw new ServiceException();
        }
    }


    @Override
    public boolean updateRatingAfterEvaluating(Integer userId, Boolean action) throws ServiceException {
        try {
            return userDao.updateRatingAfterEvaluating(action, userId);
        } catch (DAOException e) {
            LOGGER.error("Exception during data update", e);
            throw new ServiceException();
        }
    }

    @Override
    public boolean updateRating(Integer id, Double rating) throws ServiceException {
        try {
            return userDao.updateRating(id, rating);
        } catch (DAOException e) {
            LOGGER.error("Exception during data update", e);
            throw new ServiceException();
        }
    }

    private int create(User user) {
        int id = 0;
        try {
            id = userDao.create(user);
        } catch (DAOException e) {
            LOGGER.error("Exception during data update", e);

        }
        return id;
    }

}
