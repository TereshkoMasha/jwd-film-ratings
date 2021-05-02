package com.epam.dao;

import com.epam.entity.Movie;
import com.epam.entity.enums.Genre;
import com.epam.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface MovieDao {

    Optional<Movie> findByName(String name) throws DAOException;

    List<Movie> findByGenre(Genre genre) throws DAOException;

    List<Movie> findByPublicationYear(Integer year) throws DAOException;
}
