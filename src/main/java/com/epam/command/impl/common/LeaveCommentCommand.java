package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.Review;
import com.epam.entity.User;
import com.epam.entity.enums.Appraisal;
import com.epam.entity.enums.UserRole;
import com.epam.entity.enums.UserStatus;
import com.epam.exception.ServiceException;
import com.epam.service.ReviewService;
import com.epam.service.UserService;
import com.epam.service.impl.ReviewServiceImpl;
import com.epam.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LeaveCommentCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(LeaveCommentCommand.class);
    private static final ReviewService reviewService = new ReviewServiceImpl();
    private static final UserService userService = new UserServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        try {

            Integer movieId = Integer.parseInt(requestData.getRequestParameter(AttributeName.ID));

            String rating = requestData.getRequestParameter(AttributeName.RATING);

            String text = "";
            List<Review> reviewList;
            Double avrRating;
            if (requestData.getRequestParametersValues().containsKey(AttributeName.COMMENT)) {
                text = requestData.getRequestParameter(AttributeName.COMMENT).trim();
            }
            if (requestData.getSessionAttributes().containsKey(AttributeName.REVIEW)) {
                reviewList = (List<Review>) requestData.getSessionAttribute(AttributeName.REVIEW);
            } else {
                reviewList = new ArrayList<>();
            }

            User user = (User) requestData.getSessionAttribute(AttributeName.USER);
            Optional<User> optionalUser = userService.findByLogin(user.getLogin());
            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            }

            List<User> users = (List<User>) requestData.getSessionAttribute(AttributeName.USERS);
            String login = user.getLogin();

            if (user.getStatus() == UserStatus.BANNED) {
                requestData.addRequestAttribute(AttributeName.ERROR_REVIEW, "user.ban");
            } else if (users.stream().noneMatch(user1 -> user1.getLogin().equals(login))) {
                if (!requestData.getRequestParametersValues().containsKey(AttributeName.RATING)) {
                    requestData.addRequestAttribute(AttributeName.ERROR_REVIEW, "error.message.review.rating");
                } else {
                    if (reviewService.create(text, Appraisal.resolveGenreById(Integer.parseInt(rating)), movieId, user.getId())) {
                        users.add(user);
                        Collections.sort(users);
                        requestData.addSessionAttribute(AttributeName.USERS, users);
                        if ((reviewList.size() + 1) % 5 == 0) {
                            avrRating = reviewService.getAverageRating(movieId);
                            for (User userToUpdate :
                                    users) {
                                Integer movieAppraisal = 0;
                                Optional<Review> review = reviewService.findByMovieIdUserId(movieId, userToUpdate.getId());
                                if (review.isPresent()) {
                                    movieAppraisal = review.get().getRating().getId();
                                }
                                if (avrRating <= movieAppraisal + 0.5 && avrRating >= movieAppraisal - 0.5) {
                                    if (userToUpdate.getStatus() != UserStatus.HIGH && userToUpdate.getRole() != UserRole.ADMIN) {
                                        userService.updateRatingAfterEvaluating(user.getId(), true);
                                    }
                                } else {
                                    userService.updateRatingAfterEvaluating(user.getId(), false);
                                }
                            }
                        }
                    }
                }
            } else {
                requestData.addRequestAttribute(AttributeName.ERROR_REVIEW, "error.message.review");
            }

            requestData.addRequestAttribute(AttributeName.RATING, reviewService.getAverageRating(movieId));
            if (!requestData.getRequestAttributeValues().containsKey(AttributeName.ERROR_REVIEW)) {
                Optional<Review> newReview = reviewService.findByMovieIdUserId(movieId, user.getId());
                newReview.ifPresent(reviewList::add);
            }

            requestData.addRequestAttribute(AttributeName.ID, movieId);

            List<Review> reviewListWithText = reviewList.stream().filter(review -> !review.getText().isEmpty()).sorted().collect(Collectors.toList());
            requestData.addSessionAttribute(AttributeName.REVIEW, reviewListWithText);

            requestData.addRequestAttribute(AttributeName.APPRAISAL, reviewService.findAllByMovieId(movieId).size());


        } catch (ServiceException e) {
            LOGGER.error("Error adding comment", e);
            return new CommandExecute(RouteType.REDIRECT, Destination.ERROR.getPath());
        }
        return new CommandExecute(RouteType.FORWARD, Destination.MOVIE_PAGE.getPath());
    }
}
