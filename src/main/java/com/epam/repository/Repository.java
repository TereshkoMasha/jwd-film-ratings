package com.epam.repository;

import java.util.List;

public interface Repository <T,K> {
    T add(T entity);
    void remove(K id);
    T update(T entity);
    T findById(K id);
    List<T> findAll();
    long count();
}
