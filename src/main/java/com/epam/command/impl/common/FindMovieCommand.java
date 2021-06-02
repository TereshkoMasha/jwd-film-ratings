package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.Film;
import com.epam.service.FilmService;
import com.epam.service.impl.FilmServiceImpl;

import java.util.Optional;

public class FindMovieCommand implements CommandRequest {
    FilmService filmService = new FilmServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        String filmName = requestData.getRequestParameter(AttributeName.FILM).trim();
        Optional<Film> optionalFilm = filmService.findByName(filmName);
        if (optionalFilm.isPresent()) {
            requestData.addSessionAttribute("film", optionalFilm.get());
            return new CommandExecute(RouteType.FORWARD, Destination.MOVIE_PAGE.getPath());
        } else {
            return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
        }
    }
}
