package com.epam.service.impl;

import com.epam.entity.Review;
import com.epam.entity.enums.Appraisal;
import com.epam.exception.ServiceException;
import com.epam.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReviewServiceImplTest {
    private final ReviewService reviewService = new ReviewServiceImpl();

    @Test
    void findAllByMovieId() throws ServiceException {
        Integer id = 4;
        Review reviewMock = Mockito.mock(Review.class);
        Mockito.when(reviewMock.getMovieID()).thenReturn(id);
        assertEquals(reviewService.findAllByMovieId(id).get(0).getMovieID(), reviewMock.getMovieID());
    }

    @Test
    void findByMovieIdUserId() throws ServiceException {
        Integer movie_id = 1;
        Integer user_id = 1;
        Review reviewMock = Mockito.mock(Review.class);
        Mockito.when(reviewMock.getMovieID()).thenReturn(movie_id);
        Mockito.when(reviewMock.getUserID()).thenReturn(user_id);
        Review review = reviewService.findByMovieIdUserId(movie_id, user_id).get();
        assertEquals(review.getMovieID(), reviewMock.getMovieID());
        assertEquals(review.getUserID(), reviewMock.getUserID());
    }

    @Test
    void create() throws ServiceException {
        reviewService.create("Good movie", Appraisal.GOOD, 1, 2);
        assertTrue(reviewService.findByMovieIdUserId(1, 2).isPresent());
    }

    @Test
    void getAverageRating() throws ServiceException {
        assertEquals(reviewService.getAverageRating(8), 0.0);
    }

    @Test
    void deleteByUserIdMovieID() throws ServiceException {
        List<Review> reviewList = reviewService.findAll();
        if (!reviewList.isEmpty()) {
            Review review = reviewList.get(0);
            assertTrue(reviewService.deleteByMovieIdUserId(review.getMovieID(), review.getUserID()));
        }
    }
}