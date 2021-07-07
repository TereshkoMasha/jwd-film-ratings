package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.Movie;
import com.epam.entity.MovieCrewMember;
import com.epam.entity.Review;
import com.epam.entity.User;
import com.epam.exception.ServiceException;
import com.epam.service.MovieCrewService;
import com.epam.service.MovieService;
import com.epam.service.ReviewService;
import com.epam.service.UserService;
import com.epam.service.impl.MovieCrewServiceImpl;
import com.epam.service.impl.MovieServiceImpl;
import com.epam.service.impl.ReviewServiceImpl;
import com.epam.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ViewMovieInfoCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(ViewMovieInfoCommand.class);
    MovieService movieService = new MovieServiceImpl();
    MovieCrewService movieCrewService = new MovieCrewServiceImpl();
    ReviewService reviewService = new ReviewServiceImpl();
    UserService userService = new UserServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        try {
            Optional<Movie> movieOptional;
            Integer movieID;
            if (requestData.getRequestParametersValues().containsKey(AttributeName.ID)) {
                movieID = Integer.parseInt(requestData.getRequestParameter(AttributeName.ID));
                movieOptional = movieService.getById(movieID);
            } else {
                Movie movie = (Movie) requestData.getSessionAttribute(AttributeName.MOVIE);
                movieID = movie.getId();
                movieOptional = Optional.of(movie);
            }

            movieOptional.ifPresent(movie -> requestData.addSessionAttribute(AttributeName.MOVIE, movie));

            movieOptional.ifPresent(value -> {
                try {
                    if (requestData.getRequestParametersValues().containsKey(AttributeName.PAGE)) {
                        String page = requestData.getRequestParameter(AttributeName.PAGE);
                        requestData.addSessionAttribute(AttributeName.PAGE, Integer.parseInt(page));
                    }

                    List<MovieCrewMember> filmActors = movieCrewService.findAllActorsByMovieId(value.getId());
                    if (!filmActors.isEmpty()) {
                        requestData.addRequestAttribute(AttributeName.ACTOR, filmActors);
                    }
                    MovieCrewMember director = movieCrewService.findDirectorByMovieId(value.getId());
                    if (director.getName() != null) {
                        requestData.addRequestAttribute(AttributeName.DIRECTOR, director);
                    }

                    List<Review> reviewList = reviewService.findAllByMovieId(movieOptional.get().getId());

                    Double averageRating = reviewService.getAverageRating(movieID);
                    if (averageRating != null && averageRating != 0.0 && !reviewList.isEmpty()) {
                        BigDecimal bd = new BigDecimal(Double.toString(averageRating));
                        bd = bd.setScale(2, RoundingMode.HALF_UP);
                        requestData.addRequestAttribute(AttributeName.RATING, bd.doubleValue());
                    }

                    List<Review> reviewListWithText = reviewList.stream().filter(review -> !review.getText().isEmpty()).collect(Collectors.toList());
                    requestData.addSessionAttribute(AttributeName.REVIEW, reviewListWithText);

                    List<User> users = userService.findAllUsersByMovieId(movieID);
                    requestData.deleteSessionAttribute(AttributeName.USERS);
                    requestData.addSessionAttribute(AttributeName.USERS, users);
                    if (!reviewList.isEmpty()) {
                        requestData.addRequestAttribute("appraisalNumber", reviewList.size());
                    }

                    if (requestData.getSessionAttributes().containsKey(AttributeName.ERROR_REVIEW)) {
                        requestData.addRequestAttribute(AttributeName.ERROR_REVIEW, requestData.getSessionAttribute(AttributeName.ERROR_REVIEW));
                        requestData.deleteSessionAttribute(AttributeName.ERROR_REVIEW);
                    }

                } catch (ServiceException e) {
                    LOGGER.error("Error while trying to load movie page", e);
                }
            });
        } catch (Exception e) {
            return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
        }
        return new CommandExecute(RouteType.FORWARD, Destination.MOVIE_PAGE.getPath());
    }
}
