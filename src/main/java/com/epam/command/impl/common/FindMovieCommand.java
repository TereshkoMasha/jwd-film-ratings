package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.Movie;
import com.epam.exception.ServiceException;
import com.epam.service.MovieService;
import com.epam.service.impl.MovieServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class FindMovieCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(FindMovieCommand.class);
    MovieService movieService = new MovieServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        if (!requestData.getSessionAttributes().containsKey("page")) {
            requestData.addSessionAttribute("page", 1);
        }
        String filmName = requestData.getRequestParameter(AttributeName.MOVIE);
        try {
            Optional<Movie> optionalFilm = movieService.findByName(filmName);
            if (optionalFilm.isPresent()) {
                requestData.addSessionAttribute("movie", optionalFilm.get());
                return new CommandExecute(RouteType.FORWARD, Destination.MOVIE_PAGE.getPath());
            } else {
                return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
            }
        } catch (ServiceException e) {
            LOGGER.error("Movie Search Error", e);
            return new CommandExecute(RouteType.REDIRECT, Destination.ERROR.getPath());
        }
    }
}
