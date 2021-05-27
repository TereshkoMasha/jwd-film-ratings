package com.epam.service.impl;

import com.epam.entity.enums.Genre;
import com.epam.service.GenreService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenreServiceImplTest {
    private final GenreService genreService = new GenreServiceImpl();

    @Test
    void findAll() {
        assertEquals(genreService.findAll().get(0), Genre.CARTOON);
    }
}