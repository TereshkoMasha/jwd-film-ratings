package com.epam.service;

import com.epam.entity.Review;

import java.util.List;

public interface ReviewService extends BaseService<Review> {
    List<Review> findAllByMovieId(Integer id);
}
