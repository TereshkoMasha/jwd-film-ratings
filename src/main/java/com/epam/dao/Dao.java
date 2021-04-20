package com.epam.dao;

import com.epam.entity.BaseEntity;
import com.epam.exception.DAOException;

import java.util.List;
import java.util.Optional;

/**
 * The interface provide CRUD operations
 */

/**
 * DAO абстрагирует сущности системы и делает их отображение на БД,
 * определяет общие методы использования соединения, его получение,
 */

public interface Dao<T extends BaseEntity, K> {

    void create(T entity) throws DAOException;

    Optional<T> getById(K id);

    T update(T entity) throws DAOException;

    void deleteById(K id) throws DAOException;

    List<T> findAll() throws DAOException;

}
