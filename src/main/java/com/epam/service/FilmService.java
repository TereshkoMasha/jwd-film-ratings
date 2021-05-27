package com.epam.service;

import com.epam.entity.Country;
import com.epam.entity.Film;
import com.epam.entity.enums.Genre;
import com.epam.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    List<Film> findAll();

    Optional<Film> findByName(String name) throws DAOException;

    List<Film> findAllByGenre(Genre genre) throws DAOException;

    List<Film> findAllByPublicationYear(Integer year) throws DAOException;

    List<Film> findAllByCountry(Country country) throws DAOException;
}
