package com.epam.service.impl;

import com.epam.entity.Movie;
import com.epam.entity.enums.Genre;
import com.epam.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MovieServiceImplTest {
    private final MovieService movieService = new MovieServiceImpl();

    @Test
    void findAll() {
        Movie movieMock = Mockito.mock(Movie.class);
        Mockito.when(movieMock.getName()).thenReturn("Джанго освобожденный");
        Mockito.when(movieMock.getGenre()).thenReturn(Genre.THRILLER);
        assertEquals(movieService.findAll().get(0).getName(), movieMock.getName());
        assertEquals(movieService.findAll().get(0).getGenre(), movieMock.getGenre());
    }

    @Test
    void findByNamePositive()  {
        assertEquals(movieService.findByName("Легенда о волках").get().getName(), "Легенда о волках");
    }

    @Test
    void findAllByGenre() {
        Movie movieMock = Mockito.mock(Movie.class);
        Mockito.when(movieMock.getGenre()).thenReturn(Genre.CARTOON);
        assertEquals(movieService.findAllByGenre(Genre.CARTOON).get(0).getGenre(), movieMock.getGenre());
    }

    @Test
    void findAllByPublicationYear() {
        Movie movieMock = Mockito.mock(Movie.class);
        Mockito.when(movieMock.getReleaseYear()).thenReturn(2020);
        assertEquals(movieService.findAllByPublicationYear(2020).get(0).getReleaseYear(), movieMock.getReleaseYear());
    }

    @Test
    void getById() {
        Movie movieMock = Mockito.mock(Movie.class);
        Mockito.when(movieMock.getName()).thenReturn("Легенда о волках");
        Mockito.when(movieMock.getGenre()).thenReturn(Genre.CARTOON);
        assertEquals(movieService.getById(10).get().getName(), movieMock.getName());
        assertEquals(movieService.getById(10).get().getGenre(), movieMock.getGenre());
    }
}