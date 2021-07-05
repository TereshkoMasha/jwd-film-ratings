package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.Review;
import com.epam.entity.User;
import com.epam.entity.enums.Appraisal;
import com.epam.exception.ServiceException;
import com.epam.service.ReviewService;
import com.epam.service.impl.ReviewServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LeaveCommentCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(LeaveCommentCommand.class);
    ReviewService reviewService = new ReviewServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        try {
            String text = "";
            List<Review> reviewList;
            Double avrRating;
            if (requestData.getRequestParametersValues().containsKey(AttributeName.COMMENT)) {
                text = requestData.getRequestParameter(AttributeName.COMMENT).trim();
            }
            String rating = requestData.getRequestParameter(AttributeName.RATING);
            Integer movieId = Integer.parseInt(requestData.getRequestParameter(AttributeName.ID));
            List<User> users = (List<User>) requestData.getSessionAttribute(AttributeName.USERS);
            User user = (User) requestData.getSessionAttribute(AttributeName.USER);
            if (!users.contains(user)) {
                if (!requestData.getRequestParametersValues().containsKey(AttributeName.RATING)) {
                    requestData.addRequestAttribute(AttributeName.ERROR_REVIEW, "error.message.review.rating");
                } else {
                    ////////////////////////////////////////////
//                    if (avrRating <= Double.parseDouble(rating) + 0.5 && avrRating >= Double.parseDouble(rating) - 0.5) {
//                        if ((user.getStatus() != UserStatus.HIGH || user.getStatus() != UserStatus.BANNED) && userService.updateRatingAfterEvaluating(user.getId(), true))
//                            requestData.addSessionAttribute(AttributeName.USER, user);
//                    }
                    //////////////////////////////
                    if (reviewService.create(text, Appraisal.resolveGenreById(Integer.parseInt(rating)), movieId, user.getId())) {
                        users.add(user);
                        requestData.addSessionAttribute(AttributeName.USERS, users);

                    }
                }
            } else {
                requestData.addRequestAttribute(AttributeName.ERROR_REVIEW, "error.message.review");
            }
            if (requestData.getSessionAttributes().containsKey(AttributeName.REVIEW)) {
                reviewList = (List<Review>) requestData.getSessionAttribute(AttributeName.REVIEW);
                avrRating = (Double) requestData.getSessionAttribute(AttributeName.RATING);
            } else {
                reviewList = new ArrayList<>();
                avrRating = 0.0;
            }
            requestData.addRequestAttribute(AttributeName.RATING, reviewService.getAverageRating(movieId));

            if (!requestData.getRequestAttributeValues().containsKey(AttributeName.ERROR_REVIEW)) {
                Optional<Review> newReview = reviewService.findByMovieIdUserId(movieId, user.getId());
                newReview.ifPresent(reviewList::add);
            }

            requestData.addRequestAttribute("appraisalNumber", reviewList.size());
            List<Review> reviewListWithText = reviewList.stream().filter(review -> !review.getText().isEmpty()).collect(Collectors.toList());
            requestData.addRequestAttribute(AttributeName.REVIEW, reviewListWithText);

        } catch (ServiceException e) {
            LOGGER.error("Error adding comment", e);
            return new CommandExecute(RouteType.REDIRECT, Destination.ERROR.getPath());
        }
        return new CommandExecute(RouteType.FORWARD, Destination.MOVIE_PAGE.getPath());
    }
}
