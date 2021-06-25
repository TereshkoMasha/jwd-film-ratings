package com.epam.service;

import com.epam.entity.Country;
import com.epam.entity.Movie;
import com.epam.entity.enums.Genre;
import com.epam.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface MovieService extends BaseService<Movie> {

    Optional<Movie> findByName(String name) throws ServiceException;

    List<Movie> findAllByGenre(Genre genre) throws ServiceException;

    List<Movie> findAllByPublicationYear(Integer year) throws ServiceException;

    List<Movie> findAllByCountry(Country country) throws ServiceException;

}
