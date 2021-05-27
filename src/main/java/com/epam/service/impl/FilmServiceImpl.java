package com.epam.service.impl;

import com.epam.dao.impl.FilmDaoImpl;
import com.epam.entity.Country;
import com.epam.entity.Film;
import com.epam.entity.enums.Genre;
import com.epam.exception.DAOException;
import com.epam.service.FilmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FilmServiceImpl implements FilmService {
    private static final Logger LOGGER = LogManager.getLogger(FilmServiceImpl.class);
    private static final FilmDaoImpl filmDao = FilmDaoImpl.INSTANCE;

    @Override
    public List<Film> findAll() {
        List<Film> filmList = null;
        try {
            filmList = filmDao.findAll();
            if (!filmList.isEmpty()) {
                return filmList;
            }
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return filmList;
    }

    @Override
    public Optional<Film> findByName(String name) {
        try {
            return filmDao.findByName(name);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return Optional.empty();
    }

    @Override
    public List<Film> findAllByGenre(Genre genre) {
        try {
            return filmDao.findAllByGenre(genre);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Film> findAllByPublicationYear(Integer year) {
        try {
            return filmDao.findAllByPublicationYear(year);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<Film> findAllByCountry(Country country) throws DAOException {
        try {
            return filmDao.findAllByCountry(country);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return Collections.emptyList();
    }
}
