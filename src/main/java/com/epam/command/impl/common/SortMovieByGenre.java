package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.Movie;
import com.epam.entity.enums.Genre;
import com.epam.service.MovieService;
import com.epam.service.impl.MovieServiceImpl;

import java.util.List;

public class SortMovieByGenre implements CommandRequest {
    MovieService movieService = new MovieServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        String genre = requestData.getRequestParameter(AttributeName.GENRE);
        List<Movie> movieList = movieService.findAllByGenre(Genre.resolveGenreByName(genre));
        if (!movieList.isEmpty()) {
            requestData.addSessionAttribute("movies", movieList);
        }
        return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
    }
}
