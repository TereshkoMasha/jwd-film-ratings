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

    public UserDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }

    private static final String SQL_CREATE = "INSERT INTO user (name, password, login, role_id, status_id rating)" +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE_PASSWORD = "UPDATE user SET password = ? WHERE id = ? ";
    private static final String SQL_UPDATE_LOGIN = "UPDATE user SET login = ? WHERE password = ? ";
    private static final String SQL_UPDATE_STATUS = "UPDATE user SET status = ? WHERE id = ? ";

    private static final String SQL_DELETE = "DELETE FROM user WHERE id = ? ";

    private static final String SQL_FIND_ALL = "SELECT * FROM user";
    private static final String SQL_FIND_ALL_BY_ROLE_ID = "SELECT * FROM user WHERE role_id = ?";
    private static final String SQL_FIND_BY_ID = "SELECT * FROM user WHERE id = ? ";
    private static final String SQL_FIND_ALL_BY_RATING = "SELECT * FROM user WHERE rating > ?";
    private static final String SQL_FIND_BY_LOGIN_PASSWORD = "SELECT * FROM user WHERE login= ? AND password= ?";
    private static final String SQL_FIND_BY_LOGIN = "SELECT * FROM user WHERE login= ?";

    @Override
    protected void prepareStatement(PreparedStatement preparedStatement, User entity) throws SQLException {
        preparedAllUserStatements(preparedStatement, entity);
    }

    protected String getSqlFindByLoginPassword() {
        return SQL_FIND_BY_LOGIN_PASSWORD;
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


    protected String getFindALLByRoleId() {
        return SQL_FIND_ALL_BY_ROLE_ID;
    }

    protected String getFindAllByRatingSql() {
        return SQL_FIND_ALL_BY_RATING;
    }

    protected static String getUpdateLoginSql() {
        return SQL_UPDATE_LOGIN;
    }

    protected static String getUpdateStatusSql() {
        return SQL_UPDATE_STATUS;
    }

    protected static String getFindByLoginSql() {
        return SQL_FIND_BY_LOGIN;
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

    private void preparedAllUserStatements(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getLogin());
        preparedStatement.setInt(4, user.getRole().getId());
        preparedStatement.setInt(5, user.getStatus().getId());
        preparedStatement.setDouble(6, user.getRating());
    }

    @Override
    public boolean update(User entity) throws DAOException {
        var updated = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getUpdateSql())) {
                preparedStatement.setString(1, entity.getPassword());
                preparedStatement.setInt(2, entity.getID());
                if (preparedStatement.executeUpdate() != 0) {
                    updated = true;
                }
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Failed to update entity", new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return updated;
    }

    @Override
    public List<User> findAllByRoleID(Integer id) {
        List<User> userList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindALLByRoleId())) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Optional<User> optionalUser = parseResultSet(resultSet);
                    optionalUser.ifPresent(userList::add);
                }
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return userList;
    }

    @Override
    public Integer getUserRoleId(String login) throws DAOException {
        var roleId = 0;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindByLoginSql())) {
                statement.setString(1, login);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    roleId = resultSet.getInt("role_id");
                }
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return roleId;
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        Optional<User> entityOptional = Optional.empty();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getFindByLoginSql())) {
                preparedStatement.setString(1, login);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    entityOptional = parseResultSet(resultSet);
                }
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e));
            Thread.currentThread().interrupt();
        }
        return entityOptional;
    }

    @Override
    public boolean findUserByLoginAndPassword(String login, String password) throws DAOException {
        boolean state = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getSqlFindByLoginPassword())) {
                statement.setString(1, login);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();
                state = resultSet.next();
            }

        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return state;
    }

    @Override
    public boolean updateUserStatus(UserStatus userStatus, Integer id) throws DAOException {
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getUpdateStatusSql())) {
                Optional<User> user = getById(id);
                if (user.isPresent()) {
                    preparedStatement.setInt(1, userStatus.getId());
                    preparedStatement.setInt(2, id);
                    if (preparedStatement.executeUpdate() != 0) {
                        return true;
                    }
                }
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Failed to update entity", new DAOException(e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return false;
    }
}
