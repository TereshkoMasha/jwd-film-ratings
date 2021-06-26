package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.exception.ServiceException;
import com.epam.service.GenreService;
import com.epam.service.MovieService;
import com.epam.service.impl.GenreServiceImpl;
import com.epam.service.impl.MovieServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class MainCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(MainCommand.class);
    MovieService movieService = new MovieServiceImpl();
    GenreService genreService = new GenreServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {

        if (!requestData.getSessionAttributes().containsKey(AttributeName.PAGE)) {
            requestData.addSessionAttribute(AttributeName.PAGE, 1);
        } else if (requestData.getRequestParametersValues().containsKey(AttributeName.PAGE)) {
            String page = requestData.getRequestParameter(AttributeName.PAGE);
            requestData.addSessionAttribute(AttributeName.PAGE, Integer.parseInt(page));
            return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
        }
        try {
            if (!requestData.getRequestParametersValues().containsKey("movies") && !requestData.getRequestParametersValues().containsKey("actors")) {
                requestData.addSessionAttribute("movies", movieService.findAll());
                requestData.addSessionAttribute("genres", genreService.findAll());
            }
        } catch (ServiceException e) {
            LOGGER.error("Error while trying to load information for the page", e);
            return new CommandExecute(RouteType.REDIRECT, Destination.ERROR.getPath());
        }
        return new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());
    }
}
