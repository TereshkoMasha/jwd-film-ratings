package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.User;
import com.epam.entity.enums.UserRole;
import com.epam.exception.DAOException;
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
        CommandExecute commandExecute = null;
        String login = requestData.getRequestParameter(AttributeName.LOGIN).trim();
        String password = requestData.getRequestParameter(AttributeName.PASSWORD);
        try {
            if (userService.findUser(login, password)) {
                Optional<User> optionalUser = userService.findByLogin(login);
                if (optionalUser.isPresent()) {
                    UserRole role = optionalUser.get().getRole();
                    User user = optionalUser.get();
                    requestData.addSessionAttribute(AttributeName.USER, user);
                    switch (role) {
                        case USER: {
                            commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());
                            break;
                        }
                        case ADMIN: {
                            break;
                        }
                    }
                }
            } else {
                commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.LOGIN.getPath());
            }
        } catch (DAOException e) {
            LOGGER.error("Error while login", e);
            commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());
        }
        return commandExecute;
    }
}
