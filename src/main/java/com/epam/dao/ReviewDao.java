package com.epam.dao;

import com.epam.entity.Review;
import com.epam.entity.User;
import com.epam.exception.DAOException;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    List<Review> findAllByMovieId(Integer movieId) throws DAOException;

    List<Review> findAllByUserId(Integer userId) throws DAOException;

    Optional<Review> findByMovieIdUserId(Integer movieId, Integer userId) throws DAOException;

    Double getAverageRating(Integer movieId) throws DAOException;

    boolean deleteByMovieIdUserId(Integer movieId, Integer userID) throws  DAOException;

}
