package com.epam.dao.impl;

import com.epam.dao.Dao;
import com.epam.db.ConnectionPool;
import com.epam.entity.BaseEntity;
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

public abstract class AbstractDaoImpl<T extends BaseEntity> implements Dao<T> {

    private static final Logger LOGGER = LogManager.getLogger();

    protected final ConnectionPool connectionPool;

    protected AbstractDaoImpl(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    private static final String SQL_SELECT_LAST_ID = "SELECT LAST_INSERT_ID();";

    protected abstract void prepareStatement(PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract Optional<T> parseResultSet(ResultSet resultSet) throws SQLException, DAOException;

    protected abstract String getUpdateSql();

    protected abstract String getFindAllSql();

    protected abstract String getCreateSql();

    protected abstract String getDeleteSql();

    protected abstract String getFindByIdSql();

    protected String getLastInsertIdSql() {
        return SQL_SELECT_LAST_ID;
    }

    public abstract boolean update(final T entity) throws DAOException;

    @Override
    public int create(final T entity) throws DAOException {
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(getCreateSql())) {
                prepareStatement(preparedStatement, entity);
                preparedStatement.execute();
                int id = -1;
                try (PreparedStatement statement = connection.prepareStatement(getLastInsertIdSql())) {
                    ResultSet resultSet = statement.executeQuery();
                    while (resultSet.next()) {
                        id = resultSet.getInt("last_insert_id()");
                    }
                    connection.commit();
                    connection.setAutoCommit(true);
                    return id;
                }
            }

        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Failed to create entity", new DAOException(e));
            Thread.currentThread().interrupt();
        }
        return -1;
    }

    @Override
    public List<T> findAll() throws DAOException {
        List<T> entitiesList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(getFindAllSql())) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Optional<T> entityOptional = parseResultSet(resultSet);
                    entityOptional.ifPresent(entitiesList::add);
                }
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e));
            Thread.currentThread().interrupt();
        }
        return entitiesList;
    }

    @Override
    public boolean deleteById(Integer id) throws DAOException {
        boolean deleted = false;
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(getDeleteSql())) {
                preparedStatement.setInt(1, id);
                preparedStatement.execute();
                deleted = true;
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Failed to delete entity", new DAOException(e));
            Thread.currentThread().interrupt();
        }
        return deleted;
    }

    @Override
    public Optional<T> getById(Integer id) throws DAOException {
        Optional<T> entityOptional = Optional.empty();
        try (Connection connection = connectionPool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement(getFindByIdSql())) {
                preparedStatement.setInt(1, id);
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
}