package com.epam.service;

import com.epam.entity.User;
import com.epam.entity.enums.UserStatus;
import com.epam.exception.ServiceException;

import java.util.Optional;

public interface UserService extends BaseService<User> {
    /**
     * Creates and returns autogenerated (@code User) id
     *
     * @param login    unique value
     * @param password a password value
     * @param name     a first name  + surname value
     * @return (@code int) user id
     * @throws ServiceException if {@code DaoException} occurs (database access error)
     */
    int registerUser(String login, String password, String name) throws ServiceException;

    /**
     * Updates user password
     *
     * @param password value to change password
     * @return {@code true} if user rating was updated, otherwise {@code false}
     * @throws ServiceException if database access error
     */
    boolean changePassword(Integer id, String password) throws ServiceException;

    /**
     * Gets user from database
     *
     * @param login to search for a user
     * @return {@code User}
     * @throws ServiceException if database access error
     */
    Optional<User> findByLogin(String login) throws ServiceException;

    /**
     * Change the user status to blocked
     *
     * @param status new user status
     * @return {@code true} if the status has been changed, otherwise {@code false}
     * @throws ServiceException if database access error
     */
    boolean updateStatus(UserStatus status, Integer userId) throws ServiceException;

    /**
     * Updates a column rating table user
     * sets this rating by user id
     * Fired when the user rates a movie close to their average rating
     *
     * @param action to downgrade or upgrade a rating
     * @return {@code true} if user rating was updated, otherwise {@code false}
     * @throws ServiceException if database access error
     */
    boolean updateRatingAfterEvaluating(Integer userId, Boolean action) throws ServiceException;

    /**
     * Updates a column rating table user
     * sets this rating by user id
     *
     * @param rating user rating to be updated
     * @return {@code true} if user rating was updated, otherwise {@code false}
     * @throws ServiceException if database access error
     */
    boolean updateRating(Integer id, Double rating) throws ServiceException;

    /**
     * Gets user role id from database using login
     *
     * @param login to search for a user
     * @return founded user role id {@code int}
     * @throws ServiceException if database access error
     */
    int getRoleId(String login) throws ServiceException;

    /**
     * Checks if a user exists with this login
     *
     * @param login to search for a user
     * @return {@code true} if user was was found, {@code false} otherwise
     * @throws ServiceException if database access error
     */
    boolean checkLogin(String login) throws ServiceException;

    /**
     * When authorizing, it checks the presence of a user with this login and password in the database.
     *
     * @param login    to search for a user
     * @param password to search for a user
     * @return {@code true} if user was was found, {@code false} otherwise
     * @throws ServiceException if database access error
     */
    boolean findByLoginPassword(String login, String password) throws ServiceException;


}
