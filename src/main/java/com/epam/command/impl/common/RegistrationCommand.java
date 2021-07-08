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
    private static final UserService userService = new UserServiceImpl();
    private static final DataValidator dataValidator = DataValidator.getInstance();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        CommandExecute commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.MAIN_PAGE.getPath());

        if (requestData.getSessionAttributes().containsKey(AttributeName.ERROR_PASSWORD_MATCH)) {
            requestData.deleteSessionAttribute(AttributeName.ERROR_PASSWORD_MATCH);
        } else if (requestData.getSessionAttributes().containsKey(AttributeName.ERROR_LOGIN_MATCH)) {
            requestData.deleteSessionAttribute(AttributeName.ERROR_LOGIN_MATCH);
        }

        String login = requestData.getRequestParameter(AttributeName.LOGIN).trim();
        try {
            if (!userService.checkLogin(login)) {
                String password = requestData.getRequestParameter(AttributeName.PASSWORD).trim();
                String firstName = requestData.getRequestParameter(AttributeName.FIRST_NAME).trim();
                String lastName = requestData.getRequestParameter(AttributeName.LAST_NAME).trim();

                if (dataValidator.validatePasswordLogin(password, login)) {
                    int id = userService.registerUser(login, password, firstName + " " + lastName);
                    Optional<User> user = userService.getById(id);
                    user.ifPresent(value -> requestData.addSessionAttribute(AttributeName.USER, value));
                } else {
                    requestData.addRequestAttribute(AttributeName.ERROR_PASSWORD_MATCH, "error.message.valid.password");
                    commandExecute.setPagePath(Destination.LOGIN.getPath());
                    commandExecute.setRouteType(RouteType.FORWARD);
                }
            } else {
                requestData.addRequestAttribute(AttributeName.ERROR_LOGIN_MATCH, "error.message.login.match");
                commandExecute.setPagePath(Destination.LOGIN.getPath());
                commandExecute.setRouteType(RouteType.FORWARD);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error while registration process", e);
            commandExecute.setPagePath(Destination.ERROR.getPath());
        }
        return commandExecute;
    }
}
