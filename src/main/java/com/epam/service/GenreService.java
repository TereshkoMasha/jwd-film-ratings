package com.epam.service;

import com.epam.entity.enums.Genre;
import com.epam.exception.ServiceException;

import java.util.List;

public interface GenreService {

    /**
     * Finds all available genres
     *
     * @return genres wrapped in a {@link List}
     * @throws ServiceException if {@code DAOException} occurs
     */
    List<Genre> findAll() throws ServiceException;
}
