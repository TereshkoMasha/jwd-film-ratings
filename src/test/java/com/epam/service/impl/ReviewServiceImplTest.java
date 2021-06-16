package com.epam.service.impl;

import com.epam.entity.Review;
import com.epam.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReviewServiceImplTest {
    private final ReviewService reviewService = new ReviewServiceImpl();

    @Test
    void findAllByMovieId() {
        Integer id = 4;
        Review reviewMock = Mockito.mock(Review.class);
        Mockito.when(reviewMock.getMovieID()).thenReturn(id);
        assertEquals(reviewService.findAllByMovieId(id).get(0).getMovieID(), reviewMock.getMovieID());
    }
}