package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.User;
import com.epam.exception.ServiceException;
import com.epam.service.UserService;
import com.epam.service.impl.UserServiceImpl;
import com.epam.util.DataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class RegistrationCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(RegistrationCommand.class);
    UserService userService = new UserServiceImpl();
    DataValidator dataValidator = DataValidator.getInstance();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        CommandExecute commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());

        String login = requestData.getRequestParameter(AttributeName.LOGIN).trim();
        try {
            if (userService.checkLogin(login)) {
                String password = requestData.getRequestParameter(AttributeName.PASSWORD).trim();

                String firstName = requestData.getRequestParameter(AttributeName.FIRST_NAME).trim();
                String lastName = requestData.getRequestParameter(AttributeName.LAST_NAME).trim();

                if (dataValidator.validatePasswordLogin(password, login)) {
                    int id = userService.registerUser(login, password, firstName + " " + lastName);
                    Optional<User> user = userService.getById(id);
                    if (user.isPresent()) {
                        requestData.addSessionAttribute(AttributeName.USER, user);
                        commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());
                    }
                } else {

                }
            } else {
                requestData.addSessionAttribute("login-error", " ????");
            }
        } catch (ServiceException e) {
            LOGGER.error("Error while registration process", e);
            commandExecute.setPagePath(Destination.LOGIN.getPath());
        }
        return commandExecute;
    }
}
