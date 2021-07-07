package com.epam.command.impl.admin;

import com.epam.command.*;
import com.epam.entity.Review;
import com.epam.entity.User;
import com.epam.exception.ServiceException;
import com.epam.service.MovieService;
import com.epam.service.ReviewService;
import com.epam.service.UserService;
import com.epam.service.impl.MovieServiceImpl;
import com.epam.service.impl.ReviewServiceImpl;
import com.epam.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteUserComment implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(DeleteUserComment.class);
    ReviewService reviewService = new ReviewServiceImpl();
    MovieService movieService = new MovieServiceImpl();
    UserService userService = new UserServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        try {
            Integer movieId = Integer.parseInt(requestData.getRequestParameter("movieId"));
            Integer userId = Integer.parseInt(requestData.getRequestParameter("userId"));

            if (!reviewService.deleteByMovieIdUserId(movieId, userId)) {
                return new CommandExecute(RouteType.REDIRECT, Destination.ERROR.getPath());
            } else {
                requestData.addRequestAttribute(AttributeName.RATING, reviewService.getAverageRating(movieId));
                requestData.addRequestAttribute("appraisalNumber", reviewService.findAllByMovieId(movieId).size());

                List<User> users = userService.findAllUsersByMovieId(movieId);
                requestData.deleteSessionAttribute(AttributeName.USERS);
                requestData.addSessionAttribute(AttributeName.USERS, users);

                List<Review> reviewList = reviewService.findAllByMovieId(movieId);
                List<Review> reviewListWithText = reviewList.stream().filter(review -> !review.getText().isEmpty()).collect(Collectors.toList());
                requestData.addRequestAttribute(AttributeName.REVIEW, reviewListWithText);
                requestData.addRequestAttribute("reload", "reload");
            }
        } catch (ServiceException e) {
            LOGGER.error(e);
        }
        return new CommandExecute(RouteType.FORWARD, Destination.MOVIE_PAGE.getPath());
    }
}
