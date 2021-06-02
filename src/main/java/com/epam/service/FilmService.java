package com.epam.service;

import com.epam.entity.Country;
import com.epam.entity.Film;
import com.epam.entity.enums.Genre;

import java.util.List;
import java.util.Optional;

public interface FilmService {
    List<Film> findAll();

    Optional<Film> findByName(String name);

    List<Film> findAllByGenre(Genre genre);

    List<Film> findAllByPublicationYear(Integer year);

    List<Film> findAllByCountry(Country country);

    Optional<Film> findById(Integer id);
}
