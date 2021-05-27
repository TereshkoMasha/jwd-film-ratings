package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.service.FilmService;
import com.epam.service.GenreService;
import com.epam.service.impl.FilmServiceImpl;
import com.epam.service.impl.GenreServiceImpl;


public class MainCommand implements CommandRequest {
    FilmService filmService = new FilmServiceImpl();
    GenreService genreService = new GenreServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData request) {
        request.addSessionAttribute("films", filmService.findAll());
        request.addSessionAttribute("genres", genreService.findAll());
        return new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());
    }
}
