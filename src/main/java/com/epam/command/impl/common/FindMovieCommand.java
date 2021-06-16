package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.Movie;
import com.epam.service.MovieService;
import com.epam.service.impl.MovieServiceImpl;

import java.util.Optional;

public class FindMovieCommand implements CommandRequest {
    MovieService movieService = new MovieServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        String filmName = requestData.getRequestParameter(AttributeName.MOVIE);
        Optional<Movie> optionalFilm = movieService.findByName(filmName);
        if (optionalFilm.isPresent()) {
            requestData.addSessionAttribute("movie", optionalFilm.get());
            return new CommandExecute(RouteType.FORWARD, Destination.MOVIE_PAGE.getPath());
        } else {
            return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
        }
    }
}
