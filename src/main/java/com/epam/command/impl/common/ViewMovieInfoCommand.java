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
import java.util.ArrayList;
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
            Integer id = Integer.parseInt(requestData.getRequestParameter(AttributeName.ID));
            Optional<Movie> film = movieService.getById(id);
            film.ifPresent(value -> {
                try {
                    requestData.addSessionAttribute(AttributeName.MOVIE, value);
                    List<MovieCrewMember> filmActors = movieCrewService.findAllActorsByMovieId(value.getId());

                    if (requestData.getRequestParametersValues().containsKey("page")) {
                        String page = requestData.getRequestParameter("page");
                        requestData.addSessionAttribute("page", Integer.parseInt(page));
                    }

                    if (!filmActors.isEmpty()) {
                        requestData.addRequestAttribute(AttributeName.ACTOR, filmActors);
                    }

                    MovieCrewMember director = movieCrewService.findDirectorByMovieId(value.getId());
                    if (director != null) {
                        requestData.addRequestAttribute(AttributeName.DIRECTOR, director);
                    }

                    List<Review> reviewList = reviewService.findAllByMovieId(film.get().getId());

                    Double averageRating = reviewService.getAverageRating(id);
                    if (averageRating != null && averageRating != 0.0 && !reviewList.isEmpty()) {
                        BigDecimal bd = new BigDecimal(Double.toString(averageRating));
                        bd = bd.setScale(2, RoundingMode.HALF_UP);
                        requestData.addRequestAttribute(AttributeName.RATING, bd.doubleValue());
                    }

                    List<Review> reviewListWithText = reviewList.stream().filter(review -> !review.getText().isEmpty()).collect(Collectors.toList());
                    requestData.addSessionAttribute(AttributeName.REVIEW, reviewListWithText);

                    List<User> users = new ArrayList<>();
                    for (Review review :
                            reviewList) {
                        users.add(userService.getById(review.getUserID()).get());
                    }

                    requestData.addSessionAttribute(AttributeName.USERS, users);
                    requestData.addRequestAttribute("appraisalNumber", reviewList.size());

                } catch (ServiceException e) {
                    LOGGER.error("Error while trying to login", e);
                }
            });
        } catch (Exception e) {
            return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
        }
        return new CommandExecute(RouteType.FORWARD, Destination.MOVIE_PAGE.getPath());
    }
}
