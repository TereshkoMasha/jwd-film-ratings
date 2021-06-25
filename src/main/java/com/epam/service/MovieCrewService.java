package com.epam.service;

import com.epam.entity.MovieCrewMember;
import com.epam.exception.ServiceException;

import java.util.List;

public interface MovieCrewService extends BaseService<MovieCrewMember> {

    List<MovieCrewMember> findAllActorsByMovieId(Integer id) throws ServiceException;

    MovieCrewMember findDirectorByMovieId(Integer id) throws ServiceException;
}
