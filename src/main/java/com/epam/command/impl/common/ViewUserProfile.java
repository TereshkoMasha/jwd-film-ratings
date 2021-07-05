package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.Review;
import com.epam.entity.User;
import com.epam.exception.ServiceException;
import com.epam.service.ReviewService;
import com.epam.service.impl.ReviewServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ViewUserProfile implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(ViewUserProfile.class);
    ReviewService reviewService = new ReviewServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        try {
            User user = (User) requestData.getSessionAttribute("user");
            List<Review> reviewList = reviewService.findAllByUserId(user.getId());
            requestData.addRequestAttribute(AttributeName.REVIEW, reviewList);

            if (requestData.getRequestParametersValues().containsKey("page")) {
                String page = requestData.getRequestParameter("page");
                requestData.addSessionAttribute("page", Integer.parseInt(page));
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while command define", e);
            return new CommandExecute(RouteType.FORWARD, Destination.MAIN_PAGE.getPath());
        }
        return new CommandExecute(RouteType.FORWARD, Destination.PROFILE.getPath());
    }
}
