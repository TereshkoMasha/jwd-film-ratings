package com.epam.service;

import com.epam.entity.Review;
import com.epam.entity.enums.Appraisal;

import java.util.List;
import java.util.Optional;

public interface ReviewService extends BaseService<Review> {
    List<Review> findAllByMovieId(Integer id);

    boolean create(String text, Appraisal appraisal, Integer movieId, Integer userId);

    Optional<Review> findByMovieIdUserId(Integer movieId, Integer userId);

    Double getAverageRating(Integer movieId);
}
