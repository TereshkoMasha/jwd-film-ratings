package com.epam.service.impl;

import com.epam.dao.impl.FilmDaoImpl;
import com.epam.entity.Country;
import com.epam.entity.Movie;
import com.epam.entity.enums.Genre;
import com.epam.exception.DAOException;
import com.epam.service.MovieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MovieServiceImpl implements MovieService {
    private static final Logger LOGGER = LogManager.getLogger(MovieServiceImpl.class);
    private static final FilmDaoImpl filmDao = FilmDaoImpl.INSTANCE;

    @Override
    public List<Movie> findAll() {
        List<Movie> movieList = null;
        try {
            movieList = filmDao.findAll();
            if (!movieList.isEmpty()) {
                return movieList;
            }
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return movieList;
    }

    @Override
    public boolean update(Movie entity) {
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public Optional<Movie> findByName(String name) {
        try {
            return filmDao.findByName(name);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> findAllByGenre(Genre genre) {
        try {
            return filmDao.findAllByGenre(genre);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Movie> findAllByPublicationYear(Integer year) {
        try {
            return filmDao.findAllByPublicationYear(year);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Movie> findAllByCountry(Country country) {
        try {
            return filmDao.findAllByCountry(country);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Movie> getById(Integer id) {
        try {
            return filmDao.getById(id);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return Optional.empty();
    }
}
