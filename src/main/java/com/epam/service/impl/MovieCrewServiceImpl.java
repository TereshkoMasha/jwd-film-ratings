package com.epam.service.impl;

import com.epam.dao.impl.MovieCrewMemberDaoImpl;
import com.epam.entity.MovieCrewMember;
import com.epam.exception.DAOException;
import com.epam.service.MovieCrewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class MovieCrewServiceImpl implements MovieCrewService {
    private static final Logger LOGGER = LogManager.getLogger(MovieCrewService.class);
    MovieCrewMemberDaoImpl movieCrewMemberDao = MovieCrewMemberDaoImpl.INSTANCE;

    @Override
    public List<MovieCrewMember> findAll() {
        List<MovieCrewMember> movieCrewMemberList = null;
        try {
            movieCrewMemberList = movieCrewMemberDao.findAll();
            if (!movieCrewMemberList.isEmpty()) {
                return movieCrewMemberList;
            }
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return movieCrewMemberList;
    }

    @Override
    public Optional<MovieCrewMember> getById(Integer id) {
        try {
            Optional<MovieCrewMember> movieCrewMember = movieCrewMemberDao.getById(id);
            if (movieCrewMember.isPresent()) {
                return movieCrewMember;
            }
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(MovieCrewMember entity) {
        return false;
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    @Override
    public List<MovieCrewMember> findAllActorsByMovieId(Integer id) {
        try {
            List<MovieCrewMember> movieCrewMemberList = movieCrewMemberDao.findAllActorsByMovieId(id);
            if (!movieCrewMemberList.isEmpty()) {
                return movieCrewMemberList;
            }
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return Collections.emptyList();
    }

    @Override
    public MovieCrewMember findDirectorByMovieId(Integer id) {
        try {
            return movieCrewMemberDao.findDirectorByMovieId(id);
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return MovieCrewMember.builder().build();
    }
}
