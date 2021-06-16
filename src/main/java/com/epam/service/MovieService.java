package com.epam.service;

import com.epam.entity.Country;
import com.epam.entity.Movie;
import com.epam.entity.enums.Genre;

import java.util.List;
import java.util.Optional;

public interface MovieService extends BaseService<Movie> {

    Optional<Movie> findByName(String name);

    List<Movie> findAllByGenre(Genre genre);

    List<Movie> findAllByPublicationYear(Integer year);

    List<Movie> findAllByCountry(Country country);

}
