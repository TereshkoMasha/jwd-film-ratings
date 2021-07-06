package com.epam.service;

import com.epam.entity.MovieCrewMember;
import com.epam.exception.ServiceException;

import java.util.List;

public interface MovieCrewService extends BaseService<MovieCrewMember> {

    /**
     * Finds data about all the actors in a movie
     *
     * @param id the primary key of table 'movie'
     * @return the {@link MovieCrewMember} with {@link com.epam.entity.enums.MovieCrewRole} actor
     * wrapped in an {@link List}
     * @throws ServiceException if {@code DAOException} occurs
     */
    List<MovieCrewMember> findAllActorsByMovieId(Integer id) throws ServiceException;

    /**
     * Finds information about the director of a movie.
     *
     * @param id the primary key of table 'movie'
     * @return the {@link MovieCrewMember} with {@link com.epam.entity.enums.MovieCrewRole} director
     * @throws ServiceException if {@code DAOException} occurs
     */
    MovieCrewMember findDirectorByMovieId(Integer id) throws ServiceException;
}
