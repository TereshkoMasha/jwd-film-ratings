package com.epam.service.impl;

import com.epam.entity.Movie;
import com.epam.entity.enums.Genre;
import com.epam.exception.ServiceException;
import com.epam.service.MovieService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MovieServiceImplTest {

    private final MovieService movieService = new MovieServiceImpl();

    @Test
    void findByNamePositive() throws ServiceException {
        String name = "Джанго освобожденный";
        Movie movieMock = Mockito.mock(Movie.class);
        Mockito.when(movieMock.getName()).thenReturn(name);
        Optional<Movie> optionalMovie = movieService.findByName(name);
        optionalMovie.ifPresent(movie -> assertEquals(movie.getName(), movieMock.getName()));
    }

    @Test
    void findAllByGenre() throws ServiceException {
        Genre genre = Genre.THRILLER;
        List<Movie> movies = movieService.findAllByGenre(genre);
        long numberOfMoviesWithGenre = movies.stream().map(movie -> movie.getGenre() == genre).count();
        assertEquals(numberOfMoviesWithGenre, movies.size());
    }

    @Test
    void findAllByPublicationYear() throws ServiceException {
        Movie movieMock = Mockito.mock(Movie.class);
        Mockito.when(movieMock.getReleaseYear()).thenReturn(2020);
        assertEquals(movieService.findAllByPublicationYear(2020).get(0).getReleaseYear(), movieMock.getReleaseYear());
    }

    @Test
    void getById() throws ServiceException {
        Movie movieMock = Mockito.mock(Movie.class);
        Mockito.when(movieMock.getName()).thenReturn("Легенда о волках");
        Mockito.when(movieMock.getGenre()).thenReturn(Genre.CARTOON);
        Optional<Movie> movie = movieService.getById(10);
        movie.ifPresent(value -> assertEquals(value.getName(), movieMock.getName()));
        movie.ifPresent(value -> assertEquals(value.getGenre(), movieMock.getGenre()));
    }

    @Test
    void deleteById() throws ServiceException {
        List<Movie> movieList = movieService.findAll();
        Optional<Movie> movie = movieList.stream().max(Comparator.comparing(Movie::getId));
        if (movie.isPresent()) {
            Integer id = movie.get().getId();
            String name = movie.get().getName();
            if (movieService.deleteById(id)) {
                assertFalse(movieService.findByName(name).isPresent());
            }
        }
    }
}