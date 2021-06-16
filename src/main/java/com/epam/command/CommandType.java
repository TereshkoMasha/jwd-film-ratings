package com.epam.command;

import com.epam.command.impl.admin.ShowUsersCommand;
import com.epam.command.impl.common.*;

public enum CommandType {
    MAIN(new MainCommand()),
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    REGISTRATION(new RegistrationCommand()),
    SHOW_USERS(new ShowUsersCommand()),
    MOVIE_INFO(new ViewMovieInfoCommand()),
    MOVIE_SEARCH(new FindMovieCommand()),
    SORT_MOVIE_BY_GENRE(new SortMovieByGenre());

    CommandRequest command;

    CommandType(CommandRequest command) {
        this.command = command;
    }

    public CommandRequest getCommand() {
        return command;
    }
}
