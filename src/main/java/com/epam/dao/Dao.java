package com.epam.dao;

import com.epam.entity.BaseEntity;
import com.epam.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends BaseEntity> {

    void create(T entity) throws DAOException;

    boolean update(T entity) throws DAOException;

    void deleteById(Integer id) throws DAOException;

    Optional<T> getById(Integer id) throws DAOException;

    List<T> findAll() throws DAOException;

}
