package com.epam.service.impl;

import com.epam.context.impl.FilmRatingsContext;
import com.epam.dao.impl.FilmDaoImpl;
import com.epam.entity.Country;
import com.epam.entity.Movie;
import com.epam.entity.enums.Genre;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.service.MovieService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class MovieServiceImpl implements MovieService {
    private static final Logger LOGGER = LogManager.getLogger(MovieServiceImpl.class);
    private static final FilmDaoImpl filmDao = FilmDaoImpl.INSTANCE;

    @Override
    public List<Movie> findAll() throws ServiceException {
        List<Movie> movieList;
        try {
            if (FilmRatingsContext.getInstance().retrieveBaseEntityList(Movie.class).isEmpty()) {
                movieList = filmDao.findAll();
                for (Movie movie :
                        movieList) {
                    FilmRatingsContext.getInstance().retrieveBaseEntityList(Movie.class).add(movie);
                }
            }
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
        return (List<Movie>) FilmRatingsContext.getInstance().retrieveBaseEntityList(Movie.class);
    }

    @Override
    public boolean update(Movie entity) throws ServiceException {
        try {
            return filmDao.update(entity);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public boolean deleteById(Integer id) throws ServiceException {
        try {
            return filmDao.deleteById(id);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public Optional<Movie> findByName(String name) throws ServiceException {
        try {
            return filmDao.findByName(name);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public List<Movie> findAllByGenre(Genre genre) throws ServiceException {
        try {
            return filmDao.findAllByGenre(genre);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public List<Movie> findAllByPublicationYear(Integer year) throws ServiceException {
        try {
            return filmDao.findAllByPublicationYear(year);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public List<Movie> findAllByCountry(Country country) throws ServiceException {
        try {
            return filmDao.findAllByCountry(country);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public Optional<Movie> getById(Integer id) throws ServiceException {
        try {
            return filmDao.getById(id);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }
}
