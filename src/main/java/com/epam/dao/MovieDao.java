package com.epam.dao;

import com.epam.entity.Country;
import com.epam.entity.Movie;
import com.epam.entity.enums.Genre;
import com.epam.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface MovieDao {

    Optional<Movie> findByName(String name) throws DAOException;

    List<Movie> findAllByGenre(Genre genre) throws DAOException;

    List<Movie> findAllByPublicationYear(Integer year) throws DAOException;

    List<Movie> findAllByCountry(Country country) throws DAOException;
}
