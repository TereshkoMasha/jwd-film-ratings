package com.epam.service;

import com.epam.entity.BaseEntity;

import java.util.List;
import java.util.Optional;

public interface BaseService<T extends BaseEntity> {
    List<T> findAll();

    Optional<T> getById(Integer id);

    boolean update(T entity);

    boolean deleteById(Integer id);


}
