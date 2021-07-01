package com.epam.command.impl.admin;

import com.epam.command.*;
import com.epam.entity.User;
import com.epam.entity.enums.UserRole;
import com.epam.exception.ServiceException;
import com.epam.service.UserService;
import com.epam.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class ChangeUserRatingCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(ChangeUserRatingCommand.class);
    UserService userService = new UserServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        Integer id = Integer.parseInt(requestData.getRequestParameter(AttributeName.ID));
        try {
            Optional<User> optionalUser = userService.getById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (user.getRole() == UserRole.USER) {
                    List<User> users = (List<User>) requestData.getSessionAttribute("users_list");
                    Double rating = Double.parseDouble(requestData.getRequestParameter("rating"));
                    if (!rating.isNaN() && rating < 9.5 && rating > 0) {
                        for (int i = 0; i < users.size(); i++) {
                            if (users.get(i).getId().equals(id)) {
                                userService.updateRating(id, rating);
                                users.set(i, userService.getById(id).get());
                                break;
                            }
                        }
                    } else {
                        requestData.addRequestAttribute(AttributeName.ERROR_INVALID_INPUT, "error.message.invalid.input");
                    }
                    requestData.addSessionAttribute("users_list", users);
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while changing user rating", e);
            return new CommandExecute(RouteType.REDIRECT, Destination.USERS.getPath());
        } catch (NumberFormatException e) {
            requestData.addRequestAttribute(AttributeName.ERROR_INVALID_INPUT, "error.message.invalid.input");
            return new CommandExecute(RouteType.FORWARD, Destination.USERS.getPath());
        }
        return new CommandExecute(RouteType.FORWARD, Destination.USERS.getPath());
    }
}
