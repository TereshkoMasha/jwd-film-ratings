package com.epam.dao.impl;

import com.epam.dao.UserDao;
import com.epam.db.ConnectionPool;
import com.epam.entity.User;
import com.epam.entity.enums.UserRole;
import com.epam.entity.enums.UserStatus;
import com.epam.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserDaoImpl extends AbstractDaoImpl<User> implements UserDao {
    private static final Logger LOGGER = LogManager.getLogger(UserDaoImpl.class);
    public static final UserDaoImpl INSTANCE = new UserDaoImpl(ConnectionPool.getInstance());

    protected UserDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    private static final String SQL_CREATE = "INSERT INTO user ( password, login, name,role_id, status_id, rating)" +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_PASSWORD = "UPDATE user SET password = ? WHERE id = ? ";
    private static final String SQL_UPDATE_LOGIN = "UPDATE user SET login = ? WHERE password = ? ";
    private static final String SQL_UPDATE_RATING = "UPDATE user SET rating = ? WHERE id = ? ";

    private static final String SQL_DELETE = "DELETE FROM user WHERE id = ? ";

    private static final String SQL_FIND_ALL = "SELECT * FROM user";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM user WHERE id = ? ";
    private static final String SQL_FIND_BY_LOGIN = "SELECT * FROM user WHERE login= ?";
    private static final String SQL_FIND_BY_LOGIN_PASSWORD = "SELECT * FROM user WHERE login= ? AND password = ?";
    private static final String SQL_FIND_USERS_BY_MOVIE_ID = "SELECT * FROM user JOIN movie_review mr on user.id = mr.user_id where movie_id = ?";


    @Override
    protected void prepareStatement(PreparedStatement preparedStatement, User entity) throws SQLException {
        preparedStatement.setString(1, entity.getPassword());
        preparedStatement.setString(2, entity.getLogin());
        preparedStatement.setString(3, entity.getName());
        preparedStatement.setInt(4, entity.getRole().getId());
        preparedStatement.setInt(5, entity.getStatus().getId());
        preparedStatement.setDouble(6, entity.getRating());
    }

    @Override
    protected String getUpdateSql() {
        return SQL_UPDATE_PASSWORD;
    }

    @Override
    protected String getFindAllSql() {
        return SQL_FIND_ALL;
    }

    @Override
    protected String getCreateSql() {
        return SQL_CREATE;
    }

    @Override
    protected String getDeleteSql() {
        return SQL_DELETE;
    }

    @Override
    protected String getFindByIdSql() {
        return SQL_FIND_BY_ID;
    }

    protected static String getUpdateLoginSql() {
        return SQL_UPDATE_LOGIN;
    }

    protected static String getUpdateRatingSql() {
        return SQL_UPDATE_RATING;
    }

    protected static String getFindByLoginSql() {
        return SQL_FIND_BY_LOGIN;
    }


    protected static String getFindByLoginPasswordSql() {
        return SQL_FIND_BY_LOGIN_PASSWORD;
    }

    public static String getFindUsersByMovieIdSql() {
        return SQL_FIND_USERS_BY_MOVIE_ID;
    }

    @Override
    protected Optional<User> parseResultSet(ResultSet resultSet) throws SQLException {
        User user = User.builder()
                .setName(resultSet.getString("name"))
                .setLogin(resultSet.getString("login"))
                .setPassword(resultSet.getString("password"))
                .setRole(UserRole.resolveRoleById(resultSet.getInt("role_id")))
                .setStatus(UserStatus.resolveRoleById(resultSet.getInt("status_id")))
                .setRating(resultSet.getDouble("rating")).build();
        user.setId(resultSet.getInt("id"));
        return Optional.of(user);
    }

    @Override
    public boolean update(User entity) throws DAOException {
        boolean updated = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getUpdateSql())) {
                connection.setAutoCommit(false);
                preparedStatement.setString(1, entity.getPassword());
                preparedStatement.setInt(2, entity.getId());
                if (preparedStatement.executeUpdate() != 0) {
                    updated = true;
                }
                connection.commit();
                connection.setAutoCommit(true);
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Failed to update entity", new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return updated;
    }


    @Override
    public Integer getRoleId(String login) throws DAOException {
        int roleId = 0;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindByLoginSql())) {
                connection.setAutoCommit(false);
                statement.setString(1, login);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    roleId = resultSet.getInt("role_id");
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return roleId;
    }

    @Override
    public List<User> findAllByMovieId(Integer id) throws DAOException {
        List<User> userList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindUsersByMovieIdSql())) {
                connection.setAutoCommit(false);
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Optional<User> optionalUser = parseResultSet(resultSet);
                    optionalUser.ifPresent(userList::add);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return userList;
    }


    @Override
    public Optional<User> findByLogin(String login) {
        Optional<User> entityOptional = Optional.empty();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getFindByLoginSql())) {
                connection.setAutoCommit(false);
                preparedStatement.setString(1, login);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    entityOptional = parseResultSet(resultSet);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e));
            Thread.currentThread().interrupt();
        }
        return entityOptional;
    }

    @Override
    public boolean checkLogin(String login) throws DAOException {
        boolean state = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindByLoginSql())) {
                connection.setAutoCommit(false);
                statement.setString(1, login);
                ResultSet resultSet = statement.executeQuery();
                state = resultSet.next();
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return state;
    }

    @Override
    public boolean findByLoginPassword(String login, String password) throws DAOException {
        boolean state = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindByLoginPasswordSql())) {
                connection.setAutoCommit(false);
                statement.setString(1, login);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                state = resultSet.next();
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return state;
    }

    @Override
    public boolean updateStatus(UserStatus userStatus, Integer id) throws DAOException {
        boolean updated = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getUpdateRatingSql())) {
                connection.setAutoCommit(false);
                Optional<User> user = getById(id);
                if (user.isPresent()) {
                    preparedStatement.setInt(1, userStatus.getId());
                    preparedStatement.setInt(2, id);
                    if (preparedStatement.executeUpdate() != 0) {
                        connection.commit();
                        connection.setAutoCommit(true);
                        updated = true;
                    }
                }
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Failed to update entity", new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return updated;
    }

    @Override
    public boolean updateRatingAfterEvaluating(Boolean action, Integer id) throws DAOException {
        boolean updated = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getUpdateRatingSql())) {
                connection.setAutoCommit(false);
                Optional<User> user = getById(id);
                if (user.isPresent()) {
                    if (action) preparedStatement.setDouble(1, user.get().getRating() + 0.5);
                    else {
                        preparedStatement.setDouble(1, user.get().getRating() - 0.5);
                    }
                    preparedStatement.setInt(2, id);
                    if (preparedStatement.executeUpdate() != 0) {
                        connection.commit();
                        connection.setAutoCommit(true);
                        updated = true;
                    }
                }
            }

        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Failed to update entity", new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return updated;
    }

    @Override
    public boolean updateRating(Integer id, Double rating) throws DAOException {
        boolean updated = false;
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(getUpdateRatingSql())) {
                Optional<User> user = getById(id);
                if (user.isPresent()) {
                    preparedStatement.setDouble(1, rating);
                    preparedStatement.setInt(2, id);
                    if (preparedStatement.executeUpdate() != 0) {
                        connection.commit();
                        connection.setAutoCommit(true);
                        updated = true;
                    }
                }
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Failed to update entity", new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return updated;
    }
}
