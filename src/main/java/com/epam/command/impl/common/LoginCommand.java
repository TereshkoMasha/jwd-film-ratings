package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.User;
import com.epam.entity.enums.UserRole;
import com.epam.exception.ServiceException;
import com.epam.service.UserService;
import com.epam.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class LoginCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);
    UserService userService = new UserServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        CommandExecute commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());
        try {
            String login = requestData.getRequestParameter(AttributeName.LOGIN).trim();
            String password = requestData.getRequestParameter(AttributeName.PASSWORD).trim();

            if (userService.findByLoginPassword(login, password)) {
                Optional<User> optionalUser = userService.findByLogin(login);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    requestData.addSessionAttribute(AttributeName.USER, user);
                    requestData.addSessionAttribute(AttributeName.ROLE, user.getRole().getId());
                    if (user.getRole() == UserRole.ADMIN) {
                        requestData.addSessionAttribute("users_list", userService.findAll());
                        commandExecute.setPagePath(Destination.USERS.getPath());
                    }
                }
            } else {
                requestData.addRequestAttribute(AttributeName.ERROR_SIGN_IN, "error.message.login");
                commandExecute.setPagePath(Destination.LOGIN.getPath());
                commandExecute.setRouteType(RouteType.FORWARD);
            }
            return commandExecute;
        } catch (ServiceException e) {
            LOGGER.error("Error while trying to login", e);
            return new CommandExecute(RouteType.REDIRECT, Destination.ERROR.getPath());
        }
    }
}
