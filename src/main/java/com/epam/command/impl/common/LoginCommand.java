package com.epam.command.impl.common;

import com.epam.command.*;
import com.epam.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    @Override
    public CommandExecute executeCommand(HttpServletRequest request) {
        UserServiceImpl userService = new UserServiceImpl();
        String login = request.getParameter(CommandName.LOGIN);
        String password = request.getParameter(CommandName.PASSWORD);
        if (userService.findUser(login, password)) {

        }
        return new CommandExecute(RoteType.REDIRECT, Destination.LOGIN.getPath());
    }
}
