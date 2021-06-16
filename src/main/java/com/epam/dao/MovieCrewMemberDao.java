package com.epam.dao;

import com.epam.entity.MovieCrewMember;
import com.epam.exception.DAOException;

import java.util.List;

public interface MovieCrewMemberDao {

    List<MovieCrewMember> findAllActorsByMovieId(Integer id) throws DAOException;

    MovieCrewMember findDirectorByMovieId(Integer id) throws DAOException;

}
