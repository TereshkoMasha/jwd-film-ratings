package com.epam.service.impl;

import com.epam.dao.impl.ReviewDaoImpl;
import com.epam.entity.Review;
import com.epam.entity.enums.Appraisal;
import com.epam.exception.DAOException;
import com.epam.exception.ServiceException;
import com.epam.service.ReviewService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

public class ReviewServiceImpl implements ReviewService {
    private static final Logger LOGGER = LogManager.getLogger(ReviewService.class);

    private static final ReviewDaoImpl reviewDao = ReviewDaoImpl.INSTANCE;

    @Override
    public List<Review> findAll() throws ServiceException {
        List<Review> reviewList;
        try {
            reviewList = reviewDao.findAll();
            if (!reviewList.isEmpty()) {
                return reviewList;
            }
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
        return reviewList;
    }

    @Override
    public Optional<Review> getById(Integer id) throws ServiceException {
        try {
            Optional<Review> optionalReview = reviewDao.getById(id);
            if (optionalReview.isPresent()) {
                return optionalReview;
            }
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Review entity) throws ServiceException {
        try {
            return reviewDao.update(entity);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public boolean deleteById(Integer id) throws ServiceException {
        try {
            return reviewDao.deleteById(id);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public List<Review> findAllByMovieId(Integer id) throws ServiceException {
        List<Review> reviewList;
        try {
            reviewList = reviewDao.findAllByMovieId(id);
            if (!reviewList.isEmpty()) {
                return reviewList;
            }
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
        return reviewList;
    }

    @Override
    public List<Review> findAllByUserId(Integer id) throws ServiceException {
        List<Review> reviewList;
        try {
            reviewList = reviewDao.findAllByUserId(id);
            if (!reviewList.isEmpty()) {
                return reviewList;
            }
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
        return reviewList;
    }

    @Override
    public boolean create(String text, Appraisal appraisal, Integer movieId, Integer userId) throws ServiceException {
        try {
            reviewDao.create(Review.builder().setMovieID(movieId).setUserID(userId).setRating(appraisal).setText(text).build());
            return true;
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public Optional<Review> findByMovieIdUserId(Integer movieId, Integer userId) throws ServiceException {
        try {
            return reviewDao.findByMovieIdUserId(movieId, userId);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public Double getAverageRating(Integer movieId) throws ServiceException {
        try {
            Double averageRating = reviewDao.getAverageRating(movieId);
            if (!averageRating.isNaN()) {
                BigDecimal bigDecimal = BigDecimal.valueOf(averageRating);
                bigDecimal = bigDecimal.setScale(3, RoundingMode.HALF_UP);
                return bigDecimal.doubleValue();
            }
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
        return 0.0;
    }

    @Override
    public boolean deleteByMovieIdUserId(Integer movieId, Integer userId) throws ServiceException {
        try {
            return reviewDao.deleteByMovieIdUserId(movieId, userId);
        } catch (DAOException e) {
            LOGGER.error(e);
            throw new ServiceException();
        }
    }
}
