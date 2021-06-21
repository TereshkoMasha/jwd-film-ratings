package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.Movie;
import com.epam.entity.MovieCrewMember;
import com.epam.entity.Review;
import com.epam.entity.User;
import com.epam.service.MovieCrewService;
import com.epam.service.MovieService;
import com.epam.service.ReviewService;
import com.epam.service.UserService;
import com.epam.service.impl.MovieCrewServiceImpl;
import com.epam.service.impl.MovieServiceImpl;
import com.epam.service.impl.ReviewServiceImpl;
import com.epam.service.impl.UserServiceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ViewMovieInfoCommand implements CommandRequest {
    MovieService movieService = new MovieServiceImpl();
    MovieCrewService movieCrewService = new MovieCrewServiceImpl();
    ReviewService reviewService = new ReviewServiceImpl();
    UserService userService = new UserServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        Integer id = Integer.parseInt(requestData.getRequestParameter(AttributeName.ID));
        Optional<Movie> film = movieService.getById(id);
        film.ifPresent(value -> {
            requestData.addSessionAttribute(AttributeName.MOVIE, value);
            List<MovieCrewMember> filmActors = movieCrewService.findAllActorsByMovieId(value.getId());
            if (!filmActors.isEmpty()) {
                requestData.addSessionAttribute("actors", filmActors);
            }else {
                requestData.deleteSessionAttribute("actors");
            }
            MovieCrewMember director = movieCrewService.findDirectorByMovieId(value.getId());
            if (director != null) {
                requestData.addSessionAttribute("director", director);
            } else {
                requestData.deleteSessionAttribute("director");
            }
            Double averageRating = reviewService.getAverageRating(id);
            if (averageRating != null && averageRating != 0.0) {
                BigDecimal bd = new BigDecimal(Double.toString(averageRating));
                bd = bd.setScale(2, RoundingMode.HALF_UP);
                requestData.addSessionAttribute("rating", bd.doubleValue());
            } else {
                requestData.deleteSessionAttribute("rating");
            }
            List<Review> reviewList = reviewService.findAllByMovieId(film.get().getId());
            List<User> users = new ArrayList<>();
            for (Review review :
                    reviewList) {
                users.add(userService.getById(review.getUserID()).get());
            }
            requestData.addSessionAttribute("review", reviewList);
            requestData.addSessionAttribute("users", users);
        });
        return new CommandExecute(RouteType.FORWARD, Destination.MOVIE_PAGE.getPath());
    }
}
