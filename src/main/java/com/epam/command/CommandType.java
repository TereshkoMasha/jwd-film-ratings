package com.epam.command;

import com.epam.command.impl.admin.ShowUsersCommand;
import com.epam.command.impl.common.*;

public enum CommandType {
    MAIN(new MainCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new RegistrCommand()),
    SHOW_USERS(new ShowUsersCommand()),
    MOVIE_INFO(new ViewFilmInfoCommand()),
    FILM_SEARCH(new FindMovieCommand());

    CommandRequest command;

    CommandType(CommandRequest command) {
        this.command = command;
    }

    public CommandRequest getCommand() {
        return command;
    }
}
