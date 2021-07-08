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
    private static final UserService userService = new UserServiceImpl();

    /**
     * Change of user rating. The desired rating is transmitted as a request parameter.
     * The validity of the rating value is checked.
     * Administrator rating can not be changed.
     * Replaces the old user in the list, which is stored in the session attributes.
     *
     * @param requestData an object that
     *                    contains the request the client has made
     * @see UserService#updateRating(Integer, Double)
     */
    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        Integer id = Integer.parseInt(requestData.getRequestParameter(AttributeName.ID));
        try {
            Optional<User> optionalUser = userService.getById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                if (user.getRole() == UserRole.USER) {
                    List<User> users = (List<User>) requestData.getSessionAttribute(AttributeName.USERS_L);
                    Double rating = Double.parseDouble(requestData.getRequestParameter(AttributeName.RATING));

                    if (!rating.isNaN() && rating < 9.5 && rating > 0) {
                        for (int i = 0; i < users.size(); i++) {
                            if (users.get(i).getId().equals(id)) {
                                userService.updateRating(id, rating);
                                Optional<User> optional = userService.getById(id);
                                if (optional.isPresent()) {
                                    users.set(i, optional.get());
                                }
                                break;
                            }
                        }
                    } else {
                        requestData.addRequestAttribute(AttributeName.ERROR_INVALID_INPUT, "error.message.invalid.input");
                    }
                    requestData.addSessionAttribute(AttributeName.USERS_L, users);
                }
            }
        } catch (ServiceException e) {
            LOGGER.error("Exception while changing user rating", e);

        } catch (NumberFormatException e) {
            requestData.addRequestAttribute(AttributeName.ERROR_INVALID_INPUT, "error.message.invalid.input");
        }
        return new CommandExecute(RouteType.FORWARD, Destination.USERS.getPath());
    }
}
