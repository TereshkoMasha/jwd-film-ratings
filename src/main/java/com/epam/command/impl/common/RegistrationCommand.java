package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.entity.User;
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
        String password = requestData.getRequestParameter(AttributeName.PASSWORD).trim();

        String firstName = requestData.getRequestParameter(AttributeName.FIRST_NAME).trim();
        String lastName = requestData.getRequestParameter(AttributeName.LAST_NAME).trim();

        try {
            if (dataValidator.validatePasswordLogin(password, login)) {
                Optional<User> user = userService.registerUser(login, password, firstName + " " + lastName);
                if (user.isPresent()) {
                    requestData.addSessionAttribute(AttributeName.USER, user);
                    commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error while registration process", e);
            commandExecute.setPagePath(Destination.ERROR.getPath());
        }
        return commandExecute;
    }
}
