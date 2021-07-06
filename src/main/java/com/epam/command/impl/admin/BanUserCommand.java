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

public class BanUserCommand implements CommandRequest {
    private static final Logger LOGGER = LogManager.getLogger(BanUserCommand.class);
    UserService userService = new UserServiceImpl();

    @Override
    public CommandExecute executeCommand(RequestData requestData) {
        Integer id = Integer.parseInt(requestData.getRequestParameter(AttributeName.ID));
        boolean ban = Boolean.parseBoolean(requestData.getRequestParameter(AttributeName.BAN));
        try {
            if (ban) {
                userService.blockUser(UserStatus.LOW, id);
            } else {
                userService.blockUser(UserStatus.BANNED, id);
            }
            List<User> users = (List<User>) requestData.getSessionAttribute("users_list");
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getId().equals(id)) {
                    users.set(i, userService.getById(id).get());
                    break;
                }
            }
            requestData.addSessionAttribute("users_list", users);
        }catch (ServiceException e){
            LOGGER.error("Exception while changing user status", e);
            return new CommandExecute(RouteType.REDIRECT, Destination.ERROR.getPath());
        }
        return new CommandExecute(RouteType.FORWARD, Destination.USERS.getPath());
    }
}
