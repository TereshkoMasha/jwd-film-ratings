package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.Film;
import com.epam.service.FilmService;
import com.epam.service.impl.FilmServiceImpl;

public class ViewFilmInfoCommand implements CommandRequest {
    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        Integer id = Integer.parseInt(requestData.getRequestParameter(AttributeName.ID));
        FilmService filmService = new FilmServiceImpl();
        Film film = filmService.findById(id).get();
        requestData.addSessionAttribute("film", film);
        return new CommandExecute(RouteType.FORWARD, Destination.MOVIE_PAGE.getPath());
    }
}
