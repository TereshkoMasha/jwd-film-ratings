package com.epam.service.impl;

import com.epam.entity.MovieCrewMember;
import com.epam.exception.ServiceException;
import com.epam.service.MovieCrewService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class MovieCrewServiceImplTest {
    MovieCrewService movieCrewService = new MovieCrewServiceImpl();

    @Test
    void findAll() throws ServiceException {
        List<MovieCrewMember> movieCrewMemberList = movieCrewService.findAll();
        assertFalse(movieCrewMemberList.isEmpty());
    }

    @Test
    void getById() throws ServiceException {
        MovieCrewMember movieCrewMember = Mockito.mock(MovieCrewMember.class);
        Mockito.when(movieCrewMember.getName()).thenReturn("Джастин Лин");
        assertEquals(movieCrewService.getById(1).get().getName(), movieCrewMember.getName());
    }

    @Test
    void findAllActors() throws ServiceException {
        MovieCrewMember movieCrewMember = Mockito.mock(MovieCrewMember.class);
        Mockito.when(movieCrewMember.getName()).thenReturn("Вин Дизель");
        assertEquals(movieCrewService.findAllActorsByMovieId(7).get(0).getName(), movieCrewMember.getName());
    }

    @Test
    void findMovieCrew() throws ServiceException {
        MovieCrewMember movieCrewMember = Mockito.mock(MovieCrewMember.class);
        Mockito.when(movieCrewMember.getName()).thenReturn("Джастин Лин");
        assertEquals(movieCrewService.findDirectorByMovieId(7).getName(), movieCrewMember.getName());
    }
}