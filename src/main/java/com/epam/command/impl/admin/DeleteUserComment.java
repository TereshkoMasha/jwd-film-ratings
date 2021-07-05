package com.epam.command.impl.admin;

import com.epam.command.*;
import com.epam.exception.ServiceException;
import com.epam.service.ReviewService;
import com.epam.service.impl.ReviewServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteUserComment implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(DeleteUserComment.class);
    ReviewService reviewService = new ReviewServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        try {
            Integer movieId = Integer.parseInt(requestData.getRequestParameter("movieId"));
            Integer userId = Integer.parseInt(requestData.getRequestParameter("userId"));
            if (!reviewService.deleteByMovieIdUserId(movieId, userId)) {
                requestData.addRequestAttribute(AttributeName.RATING, reviewService.getAverageRating(movieId));
                return new CommandExecute(RouteType.REDIRECT, Destination.ERROR.getPath());
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
        }
        return new CommandExecute(RouteType.FORWARD, Destination.MOVIE_PAGE.getPath());
    }
}
