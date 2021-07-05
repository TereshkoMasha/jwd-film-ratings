package com.epam.command;

import com.epam.command.impl.admin.BanUserCommand;
import com.epam.command.impl.admin.ChangeUserRatingCommand;
import com.epam.command.impl.admin.DeleteUserComment;
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
    SORT_MOVIE_BY_GENRE(new SortMovieByGenre()),
    BAN_USER(new BanUserCommand()),
    LEAVE_COMMENT(new LeaveCommentCommand()),
    CHANGE_RATING(new ChangeUserRatingCommand()),
    VIEW_USER_PROFILE(new ViewUserProfile()),
    DELETE_USER_COMMENT(new DeleteUserComment()),
    CHANGE_LANGUAGE(new ChangeLanguage());

    CommandRequest command;

    CommandType(CommandRequest command) {
        this.command = command;
    }

    public CommandRequest getCommand() {
        return command;
    }
}
