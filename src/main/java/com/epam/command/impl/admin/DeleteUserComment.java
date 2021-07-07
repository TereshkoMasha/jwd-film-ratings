package com.epam.command.impl.admin;

import com.epam.command.*;
import com.epam.entity.User;
import com.epam.exception.ServiceException;
import com.epam.service.ReviewService;
import com.epam.service.UserService;
import com.epam.service.impl.ReviewServiceImpl;
import com.epam.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class DeleteUserComment implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(DeleteUserComment.class);
    ReviewService reviewService = new ReviewServiceImpl();
    UserService userService = new UserServiceImpl();

    /**
     * Removes a user's comment on a movie.
     * Modifies the session attribute with the old list of users who have left a comment for this movie.
     *
     * @param requestData an object that
     *                    contains the request the client has made
     * @see UserService#findAllUsersByMovieId(Integer)
     */
    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        try {
            Integer movieId = Integer.parseInt(requestData.getRequestParameter("movieId"));
            Integer userId = Integer.parseInt(requestData.getRequestParameter("userId"));

            if (!reviewService.deleteByMovieIdUserId(movieId, userId)) {
                return new CommandExecute(RouteType.FORWARD, Destination.ERROR.getPath());
            } else {
                requestData.addRequestAttribute(AttributeName.RATING, reviewService.getAverageRating(movieId));
                requestData.addRequestAttribute(AttributeName.APPRAISAL, reviewService.findAllByMovieId(movieId).size());

                List<User> users = userService.findAllUsersByMovieId(movieId);
                requestData.deleteSessionAttribute(AttributeName.USERS);
                requestData.addSessionAttribute(AttributeName.USERS, users);

                requestData.addRequestAttribute(AttributeName.RELOAD, AttributeName.RELOAD);
                requestData.addSessionAttribute(AttributeName.PAGE, 0);
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
        }
        return new CommandExecute(RouteType.FORWARD, Destination.MOVIE_PAGE.getPath());
    }
}
