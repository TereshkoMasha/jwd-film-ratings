package com.epam.dao;

import com.epam.db.ConnectionPool;
import com.epam.entity.BaseEntity;
import com.epam.exception.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The class provide CRUD operations for base entities
 */


public abstract class AbstractController<T extends BaseEntity, K> implements Dao<T, K> {

    protected final ConnectionPool connectionPool;

    protected AbstractController(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }


    protected abstract void prepareCreateStatement(PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract void prepareUpdateStatement(PreparedStatement preparedStatement, T entity) throws SQLException;

    protected abstract Optional<T> parseResultSet(ResultSet resultSet) throws SQLException, DAOException;

    protected abstract String getUpdateSql();

    protected abstract String getFindAllSql();

    protected abstract String getCreateSql();

    protected abstract String getDeleteSql();

    protected abstract String getFindByIdSql();


    @Override
    public void create(final T entity) throws DAOException {
        try (Connection connection = connectionPool.getConnection();) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getCreateSql())) {
                prepareCreateStatement(preparedStatement, entity);
                preparedStatement.execute();
            }
        } catch (SQLException | InterruptedException e) {
            throw new DAOException("Failed to create entity");
        }
    }

    @Override
    public T update(final T entity) throws DAOException {
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getUpdateSql())) {
                prepareUpdateStatement(preparedStatement, entity);
                preparedStatement.execute();
            }
        } catch (SQLException | InterruptedException e) {
            throw new DAOException("Failed to update entity", e);
        }
        return entity;
    }

    @Override
    public void deleteById(K id) throws DAOException {
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getDeleteSql())) {
                preparedStatement.setObject(1, id);
                preparedStatement.execute();
            }
        } catch (SQLException | InterruptedException e) {
            throw new DAOException("Failed to delete entity with id = " + id, e);
        }
    }

    @Override
    public List<T> findAll() throws DAOException {
        List<T> entitiesList = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getFindAllSql())) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Optional<T> entityOptional = parseResultSet(resultSet);
                    entityOptional.ifPresent(entitiesList::add);
                }
            }
        } catch (SQLException | InterruptedException e) {

            throw new DAOException(e);
        }
        return entitiesList;
    }

    @Override
    public Optional<T> getById(K id) {
        Optional<T> entityOptional = Optional.empty();
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(getFindByIdSql())) {
                preparedStatement.setObject(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    entityOptional = parseResultSet(resultSet);
                }
            }
        } catch (SQLException | InterruptedException | DAOException e) {
            System.out.println(e.getMessage());
        }
        return entityOptional;
    }
}
