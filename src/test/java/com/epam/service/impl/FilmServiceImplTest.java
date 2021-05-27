package com.epam.service.impl;

import com.epam.entity.Film;
import com.epam.entity.enums.Genre;
import com.epam.exception.DAOException;
import com.epam.service.FilmService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilmServiceImplTest {
    private final FilmService filmService = new FilmServiceImpl();

    @Test
    void findAll() {
        Film filmMock = Mockito.mock(Film.class);
        Mockito.when(filmMock.getName()).thenReturn("Легенда о волках");
        Mockito.when(filmMock.getGenre()).thenReturn(Genre.CARTOON);
        assertEquals(filmService.findAll().get(0).getName(), filmMock.getName());
        assertEquals(filmService.findAll().get(0).getGenre(), filmMock.getGenre());
    }

    @Test
    void findByNamePositive() throws DAOException {
        assertEquals(filmService.findByName("Легенда о волках").get().getName(), "Легенда о волках");
    }

    @Test
    void findAllByGenre() {
        Film filmMock = Mockito.mock(Film.class);
        Mockito.when(filmMock.getGenre()).thenReturn(Genre.CARTOON);
        assertEquals(filmService.findAll().get(0).getGenre(), filmMock.getGenre());
    }

    @Test
    void findAllByPublicationYear() throws DAOException {
        Film filmMock = Mockito.mock(Film.class);
        Mockito.when(filmMock.getReleaseYear()).thenReturn(2020);
        assertEquals(filmService.findAllByPublicationYear(2020).get(0).getReleaseYear(), filmMock.getReleaseYear());
    }

}