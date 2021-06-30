package com.epam.service;

import com.epam.entity.BaseEntity;
import com.epam.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseEntity> {

    List<T> findAll() throws ServiceException;

    Optional<T> getById(Integer id) throws ServiceException;

    boolean deleteById(Integer id) throws ServiceException;

    boolean update(T entity) throws ServiceException;

}
