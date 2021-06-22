package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.User;
import com.epam.service.UserService;
import com.epam.service.impl.UserServiceImpl;
import com.epam.util.DataValidator;
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
        String password = requestData.getRequestParameter(AttributeName.PASSWORD).trim();

        if (userService.findUser(login, password)) {
            Optional<User> optionalUser = userService.findByLogin(login);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                requestData.addSessionAttribute(AttributeName.USER, user);
                requestData.addSessionAttribute(AttributeName.ROLE, user.getRole().getId());
                switch (user.getRole()) {
                    case USER: {
                        commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());
                        break;
                    }
                    case ADMIN: {
                        requestData.addSessionAttribute("users_list", userService.findAll());
                        commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.USERS.getPath());
                        break;
                    }
                }
            }
        } else {
            requestData.addSessionAttribute("login-error", AttributeName.LOGIN_ERROR);
            commandExecute = new CommandExecute(RouteType.FORWARD, Destination.LOGIN.getPath());
        }

        return commandExecute;
    }
}
