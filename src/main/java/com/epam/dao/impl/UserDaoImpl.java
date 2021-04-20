package com.epam.dao.impl;

import com.epam.dao.AbstractController;
import com.epam.dao.UserDao;
import com.epam.db.ConnectionPool;
import com.epam.entity.User;
import com.epam.entity.UserRole;
import com.epam.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserDaoImpl extends AbstractController<User, Integer> implements UserDao {

    public static UserDaoImpl INSTANCE = new UserDaoImpl(ConnectionPool.getInstance());

    private UserDaoImpl(ConnectionPool connectionPool) {
        super(connectionPool);
    }


    private static final String SQL_CREATE = "INSERT INTO user (name, password, login, role_id, rating)" +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ALL = "SELECT FROM user(id, name, login, password, role_id, rating)";
    private static final String SQL_UPDATE_LOGIN = "UPDATE user SET login = ? WHERE password = ? ";
    private static final String SQL_DELETE = "DELETE FROM user WHERE id = ? ";
    private static final String SQL_FIND_BY_ID = "SELECT FROM user (name, password, login, role_id, rating) WHERE id = ?";
    private static final String SQL_FIND_ALL_BY_ROLE_ID = "SELECT FROM user (name, password, login, role_id, rating) WHERE role_id = ?";

    @Override
    protected void prepareCreateStatement(PreparedStatement preparedStatement, User entity) throws SQLException {
        preparedAllUserStatements(preparedStatement, entity);
    }

    @Override
    protected void prepareUpdateStatement(PreparedStatement preparedStatement, User entity) throws SQLException {
        preparedAllUserStatements(preparedStatement, entity);
        preparedStatement.setInt(7, entity.getID());
    }

    @Override
    protected String getUpdateSql() {
        return SQL_UPDATE_LOGIN;
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

    private void preparedAllUserStatements(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setInt(1, user.getID());
        preparedStatement.setString(2, user.getName());
        preparedStatement.setString(3, user.getPassword());
        preparedStatement.setString(4, user.getLogin());
        preparedStatement.setInt(5, user.getID());
        preparedStatement.setDouble(6, user.getRating());
    }

    @Override
    protected Optional<User> parseResultSet(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setRole(UserRole.resolveRoleById(resultSet.getInt("role_id")));
        user.setRating(resultSet.getDouble("rating"));
        return Optional.of(user);
    }

    @Override
    public List<User> findAllByRoleID(Integer id) throws DAOException {
        List<User> userList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindALLByRoleId())) {
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    Optional<User> entityOptional = parseResultSet(resultSet);
                    entityOptional.ifPresent(userList::add);
                }
            }
        } catch (SQLException | InterruptedException e) {

            throw new DAOException(e);
        }
        return userList;
    }

    @Override
    public int getUserRoleId(String login) throws DAOException {
        int roleId = 0;
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(getFindByIdSql())) {
                statement.setString(1, login);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    roleId = resultSet.getInt("role_id");
                }
            }
        } catch (SQLException | InterruptedException e) {
            throw new DAOException(e);
        }
        return roleId;
    }

    @Override
    public Optional<User> findByLogin(String login) throws DAOException {
        return Optional.empty();
    }
}
