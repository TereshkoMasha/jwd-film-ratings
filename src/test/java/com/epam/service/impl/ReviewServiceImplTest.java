package com.epam.service.impl;

import com.epam.entity.Review;
import com.epam.entity.enums.Appraisal;
import com.epam.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReviewServiceImplTest {
    private final ReviewService reviewService = new ReviewServiceImpl();

    @Test
    void findAllByMovieId() {
        Integer id = 4;
        Review reviewMock = Mockito.mock(Review.class);
        Mockito.when(reviewMock.getMovieID()).thenReturn(id);
        assertEquals(reviewService.findAllByMovieId(id).get(0).getMovieID(), reviewMock.getMovieID());
    }

    @Test
    void getByMovieIdUserId() {
        Integer movie_id = 4;
        Integer user_id = 2;
        Review reviewMock = Mockito.mock(Review.class);
        Mockito.when(reviewMock.getMovieID()).thenReturn(movie_id);
        Mockito.when(reviewMock.getUserID()).thenReturn(user_id);
        Review review = reviewService.findByMovieIdUserId(movie_id, user_id).get();
        assertEquals(review.getMovieID(), reviewMock.getMovieID());
        assertEquals(review.getUserID(), reviewMock.getUserID());
    }

    @Test
    void create() {
        reviewService.create("Good movie", Appraisal.GOOD, 1, 2);
        assertTrue(reviewService.findByMovieIdUserId(1, 2).isPresent());
    }

    @Test
    void getAverageRating() {
        assertEquals(reviewService.getAverageRating(3), 3);
    }
}