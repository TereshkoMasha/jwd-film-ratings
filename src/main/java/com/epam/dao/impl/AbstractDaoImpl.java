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

    protected abstract void prepareCreateStatement(PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract Optional<T> parseResultSet(ResultSet resultSet) throws SQLException, DAOException;

    protected abstract String getUpdateSql();

    protected abstract String getFindAllSql();

    protected abstract String getCreateSql();

    protected abstract String getDeleteSql();

    protected abstract String getFindByIdSql();


    @Override
    public void create(final T entity) throws DAOException {
        try (Connection connection = connectionPool.getConnection();) {
            try (var preparedStatement = connection.prepareStatement(getCreateSql())) {
                prepareCreateStatement(preparedStatement, entity);
                preparedStatement.execute();
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Failed to create entity", new DAOException(e));
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public boolean update(final T entity) throws DAOException {
        var updated = false;
        try (Connection connection = connectionPool.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(getUpdateSql())) {
                prepareCreateStatement(preparedStatement, entity);
                if (preparedStatement.executeUpdate() != 0) {
                    updated = true;
                }
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Failed to update entity", new DAOException());
            Thread.currentThread().interrupt();
        }
        return updated;
    }

    @Override
    public List<T> findAll() throws DAOException {
        List<T> entitiesList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(getFindAllSql())) {
                var resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Optional<T> entityOptional = parseResultSet(resultSet);
                    entityOptional.ifPresent(entitiesList::add);
                }
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error(new DAOException(e));
            Thread.currentThread().interrupt();
        }
        return entitiesList;
    }

    @Override
    public void deleteById(Integer id) {
        try (Connection connection = connectionPool.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(getDeleteSql())) {
                preparedStatement.setObject(1, id);
                preparedStatement.execute();
            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.error("Failed to delete entity", new DAOException(e));
            Thread.currentThread().interrupt();
        }
    }


    @Override
    public Optional<T> getById(Integer id) throws DAOException {
        Optional<T> entityOptional = Optional.empty();
        try (Connection connection = connectionPool.getConnection()) {
            try (var preparedStatement = connection.prepareStatement(getFindByIdSql())) {
                preparedStatement.setInt(1, id);
                var resultSet = preparedStatement.executeQuery();
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
}