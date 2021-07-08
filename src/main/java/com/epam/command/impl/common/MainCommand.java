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
    private static final MovieService movieService = new MovieServiceImpl();
    private static final GenreService genreService = new GenreServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        if (requestData.getRequestParametersValues().containsKey(AttributeName.PAGE)) {
            String page = requestData.getRequestParameter(AttributeName.PAGE);
            requestData.addSessionAttribute(AttributeName.PAGE, Integer.parseInt(page));
        }
        try {
            if ((!requestData.getSessionAttributes().containsKey(AttributeName.MOVIES) && !requestData.getSessionAttributes().containsKey(AttributeName.GENRES))
                    || !requestData.getRequestParametersValues().containsKey(AttributeName.PAGE)) {
                requestData.addSessionAttribute(AttributeName.MOVIES, movieService.findAll());
                requestData.addSessionAttribute(AttributeName.GENRES, genreService.findAll());
                requestData.addSessionAttribute(AttributeName.PAGE, 0);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error while trying to load information for the page", e);
            return new CommandExecute(RouteType.REDIRECT, Destination.ERROR.getPath());
        }
        return new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());
    }
}
