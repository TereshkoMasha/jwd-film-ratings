package com.epam.service;

import com.epam.entity.MovieCrewMember;

import java.util.List;

public interface MovieCrewService extends BaseService<MovieCrewMember> {

    List<MovieCrewMember> findAllActorsByMovieId(Integer id);

    MovieCrewMember findDirectorByMovieId(Integer id);
}
