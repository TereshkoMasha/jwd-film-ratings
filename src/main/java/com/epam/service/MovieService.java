package com.epam.service;

import com.epam.entity.Country;
import com.epam.entity.Movie;
import com.epam.entity.enums.Genre;
import com.epam.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface MovieService extends BaseService<Movie> {

    /**
     * Reads movie-data to given movie name.
     *
     * @param name - unique key of table 'movie'
     * @return the movie wrapped in an {@link Optional}
     * @throws ServiceException if {@code DaoException} occurs
     */
    Optional<Movie> findByName(String name) throws ServiceException;

    /**
     * Finds all movies by genre
     *
     * @param genre unique key of table 'genre'
     * @return films wrapped in a {@link List}
     * @throws ServiceException if {@code DaoException} occurs
     */
    List<Movie> findAllByGenre(Genre genre) throws ServiceException;

    /**
     * Finds all movies by publication year
     *
     * @param year key of table 'movie'
     * @return films wrapped in a {@link List}
     * @throws ServiceException if {@code DAOException} occurs
     */
    List<Movie> findAllByPublicationYear(Integer year) throws ServiceException;

    /**
     * Finds all movies by country
     *
     * @param country unique key of table 'country'
     * @return films wrapped in a {@link List}
     * @throws ServiceException if {@code DAOException} occurs
     */
    List<Movie> findAllByCountry(Country country) throws ServiceException;

}
