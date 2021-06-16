package com.epam.dao;

import com.epam.entity.Review;
import com.epam.exception.DAOException;

import java.util.List;

public interface ReviewDao {
    List<Review> findAllByMovieId(Integer movie_id) throws DAOException;
}
