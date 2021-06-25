package com.epam.service;

import com.epam.entity.Review;
import com.epam.entity.enums.Appraisal;
import com.epam.exception.ServiceException;

import java.util.List;
import java.util.Optional;

public interface ReviewService extends BaseService<Review> {
    List<Review> findAllByMovieId(Integer id) throws ServiceException;

    boolean create(String text, Appraisal appraisal, Integer movieId, Integer userId) throws ServiceException;

    Optional<Review> findByMovieIdUserId(Integer movieId, Integer userId) throws ServiceException;

    Double getAverageRating(Integer movieId) throws ServiceException;
}
