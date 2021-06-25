package com.epam.service.impl;

import com.epam.dao.impl.MovieCrewMemberDaoImpl;
import com.epam.entity.MovieCrewMember;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.service.MovieCrewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class MovieCrewServiceImpl implements MovieCrewService {
    private static final Logger LOGGER = LogManager.getLogger(MovieCrewService.class);
    MovieCrewMemberDaoImpl movieCrewMemberDao = MovieCrewMemberDaoImpl.INSTANCE;

    @Override
    public List<MovieCrewMember> findAll() throws ServiceException {
        List<MovieCrewMember> movieCrewMemberList;
        try {
            movieCrewMemberList = movieCrewMemberDao.findAll();
            if (!movieCrewMemberList.isEmpty()) {
                return movieCrewMemberList;
            }
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
        return movieCrewMemberList;
    }

    @Override
    public Optional<MovieCrewMember> getById(Integer id) throws ServiceException {
        try {
            Optional<MovieCrewMember> movieCrewMember = movieCrewMemberDao.getById(id);
            if (movieCrewMember.isPresent()) {
                return movieCrewMember;
            }
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
        return Optional.empty();
    }


    @Override
    public boolean deleteById(Integer id) throws ServiceException {
        return false;
    }

    @Override
    public boolean update(MovieCrewMember movieCrewMember) throws ServiceException {
        return false;
    }

    @Override
    public List<MovieCrewMember> findAllActorsByMovieId(Integer id) throws ServiceException {
        List<MovieCrewMember> movieCrewMemberList;
        try {
            movieCrewMemberList = movieCrewMemberDao.findAllActorsByMovieId(id);
            if (!movieCrewMemberList.isEmpty()) {
                return movieCrewMemberList;
            }
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
        return movieCrewMemberList;
    }

    @Override
    public MovieCrewMember findDirectorByMovieId(Integer id) throws ServiceException {
        try {
            return movieCrewMemberDao.findDirectorByMovieId(id);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }
}
