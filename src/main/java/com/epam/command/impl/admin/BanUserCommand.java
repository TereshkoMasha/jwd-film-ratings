package com.epam.command.impl.admin;

import com.epam.command.*;
import com.epam.entity.User;
import com.epam.entity.enums.UserStatus;
import com.epam.exception.ServiceException;
import com.epam.service.UserService;
import com.epam.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class BanUserCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(BanUserCommand.class);
    UserService userService = new UserServiceImpl();

    /**
     * Changing user status. By the request parameter, the blocking / unlock operation is determined.
     * When blocking the user rating is reduced to 0. When unlocking, a rating is set 1.
     *
     * @param requestData an object that
     *                    contains the request the client has made
     * @see UserService#updateStatus(UserStatus, Integer)
     */

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        Integer id = Integer.parseInt(requestData.getRequestParameter(AttributeName.ID));
        boolean ban = Boolean.parseBoolean(requestData.getRequestParameter(AttributeName.BAN));
        try {
            if (ban) {
                userService.updateStatus(UserStatus.LOW, id);
            } else {
                userService.updateStatus(UserStatus.BANNED, id);
            }
            List<User> users = (List<User>) requestData.getSessionAttribute(AttributeName.USERS_L);
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getId().equals(id)) {
                    Optional<User> optional = userService.getById(id);
                    if (optional.isPresent()) {
                        users.set(i, optional.get());
                    }
                    break;
                }
            }
            requestData.addSessionAttribute(AttributeName.USERS_L, users);
        } catch (ServiceException e) {
            LOGGER.error("Exception while changing user status", e);
            return new CommandExecute(RouteType.REDIRECT, Destination.ERROR.getPath());
        }
        return new CommandExecute(RouteType.FORWARD, Destination.USERS.getPath());
    }
}
