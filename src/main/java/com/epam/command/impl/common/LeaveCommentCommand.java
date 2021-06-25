package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.Review;
import com.epam.entity.User;
import com.epam.entity.enums.Appraisal;
import com.epam.entity.enums.UserStatus;
import com.epam.exception.ServiceException;
import com.epam.service.ReviewService;
import com.epam.service.UserService;
import com.epam.service.impl.ReviewServiceImpl;
import com.epam.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class LeaveCommentCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(LeaveCommentCommand.class);
    ReviewService reviewService = new ReviewServiceImpl();
    UserService userService = new UserServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        String text = null;
        try {
            if (requestData.getRequestParameter(AttributeName.COMMENT) != null) {
                text = requestData.getRequestParameter(AttributeName.COMMENT);
            }
            if (requestData.getRequestParameter(AttributeName.RATING) != null) {

                String rating = requestData.getRequestParameter(AttributeName.RATING);
                Double avrRating = (Double) requestData.getSessionAttribute(AttributeName.RATING);
                Integer movie_id = Integer.parseInt(requestData.getRequestParameter(AttributeName.ID));
                User user = (User) requestData.getSessionAttribute(AttributeName.USER);

                if (avrRating <= Double.parseDouble(rating) + 0.5 && avrRating >= Double.parseDouble(rating) - 0.5) {
                    if ((user.getStatus() != UserStatus.HIGH || user.getStatus() != UserStatus.BANNED) && userService.updateRatingAfterEvaluating(user.getId(), true))
                        requestData.addSessionAttribute("user", user);
                }
                List<User> users = (List<User>) requestData.getSessionAttribute("users");
                if (!users.contains(user)) {
                    users.add(user);
                    requestData.addSessionAttribute("users", users);
                    if (text != null) {
                        reviewService.create(text, Appraisal.resolveGenreById(Integer.parseInt(rating)), movie_id, user.getId());
                    } else {
                        reviewService.create("", Appraisal.resolveGenreById(Integer.parseInt(rating)), movie_id, user.getId());
                    }
                    requestData.addSessionAttribute("rating", reviewService.getAverageRating(movie_id));
                    List<Review> reviewList = (List<Review>) requestData.getSessionAttribute("review");
                    reviewList.add(reviewService.findByMovieIdUserId(movie_id, user.getId()).get());
                    requestData.addSessionAttribute("review", reviewList);
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Error adding comment", e);
            return new CommandExecute(RouteType.REDIRECT, Destination.ERROR.getPath());
        }
        return new CommandExecute(RouteType.FORWARD, Destination.MOVIE_PAGE.getPath());
    }
}
