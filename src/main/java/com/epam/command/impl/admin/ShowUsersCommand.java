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

public class ShowUsersCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(ShowUsersCommand.class);

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        CommandExecute commandExecute = null;
        try {
            UserService userService = new UserServiceImpl();
            List<User> userList = userService.findAll();
            User user = (User) requestData.getSessionAttribute("user");
            if (user.getRole() == UserRole.ADMIN) {
                if (!userList.isEmpty()) {
                    requestData.addSessionAttribute("users_list", userList);
                    commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.USERS.getPath());
                }
            } else {
                commandExecute = new CommandExecute(RouteType.REDIRECT, Destination.ERROR.getPath());
            }
        } catch (ServiceException e) {
            LOGGER.error("Error loading user list", e);
            return new CommandExecute(RouteType.REDIRECT, Destination.ERROR.getPath());
        }
        return commandExecute;
    }
}
