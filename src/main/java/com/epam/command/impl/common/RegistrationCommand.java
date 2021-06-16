package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.User;
import com.epam.service.UserService;
import com.epam.service.impl.UserServiceImpl;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public class RegistrationCommand implements CommandRequest {
    UserService userService = new UserServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        CommandExecute commandExecute = null;
        String login = requestData.getRequestParameter(AttributeName.LOGIN).trim();
        String firstName = requestData.getRequestParameter(AttributeName.FIRST_NAME).trim();
        String lastName = requestData.getRequestParameter(AttributeName.LAST_NAME).trim();
        String password = requestData.getRequestParameter(AttributeName.PASSWORD).trim();
        String email = requestData.getRequestParameter(AttributeName.EMAIL).trim();
        try {
            Optional<User> user = userService.registerUser(login, password, firstName + " " + lastName, email);
            if (user.isPresent()) {
                requestData.addSessionAttribute(AttributeName.USER, user);
                commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return commandExecute;
    }
}
