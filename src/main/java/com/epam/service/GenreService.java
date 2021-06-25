package com.epam.service;

import com.epam.entity.enums.Genre;
import com.epam.exception.ServiceException;

import java.util.List;

public interface GenreService {
    List<Genre> findAll() throws ServiceException;
}
