package com.epam.dao;

import com.epam.entity.enums.Genre;
import com.epam.exception.DAOException;

import java.util.List;

public interface GenreDao {
    List<Genre> findAll() throws DAOException;
}
