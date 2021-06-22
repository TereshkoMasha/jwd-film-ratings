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
    public CommandExecute executeCommand(RequestData requestData) {
        if (!requestData.getSessionAttributes().containsKey("page")) {
            requestData.addSessionAttribute("page", 1);
        } else if (requestData.getRequestParametersValues().containsKey("page")) {
            String page = requestData.getRequestParameter("page");
            requestData.addSessionAttribute("page", Integer.parseInt(page));
            return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
        }
        if (!requestData.getRequestParametersValues().containsKey("movies") && !requestData.getRequestParametersValues().containsKey("actors")) {
            requestData.addSessionAttribute("movies", movieService.findAll());
            requestData.addSessionAttribute("genres", genreService.findAll());
        }
        return new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());
    }
}
