package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.Movie;
import com.epam.entity.enums.Genre;
import com.epam.exception.ServiceException;
import com.epam.service.MovieService;
import com.epam.service.impl.MovieServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SortMovieByGenre implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(SortMovieByGenre.class);
    MovieService movieService = new MovieServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        try {
            String genre = requestData.getRequestParameter(AttributeName.GENRE);
            List<Movie> movieList = movieService.findAllByGenre(Genre.resolveGenreByName(genre));
            if (!movieList.isEmpty()) {
                requestData.addSessionAttribute("movies", movieList);
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while command define", e);
            return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
        }

        return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
    }
}
