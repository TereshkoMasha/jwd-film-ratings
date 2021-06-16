package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.service.GenreService;
import com.epam.service.MovieService;
import com.epam.service.impl.GenreServiceImpl;
import com.epam.service.impl.MovieServiceImpl;


public class MainCommand implements CommandRequest {
    MovieService movieService = new MovieServiceImpl();
    GenreService genreService = new GenreServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData request) {
        request.addSessionAttribute("movies", movieService.findAll());
        request.addSessionAttribute("genres", genreService.findAll());
        return new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());
    }
}
